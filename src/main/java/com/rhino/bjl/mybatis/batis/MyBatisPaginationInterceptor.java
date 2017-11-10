package com.rhino.bjl.mybatis.batis;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * batis拦截器
 * @author wu
 * @version 1.0.0
 */
@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MyBatisPaginationInterceptor implements Interceptor {
	protected static Logger log = LoggerFactory
			.getLogger(MyBatisPaginationInterceptor.class);

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
				SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
				.getValue("delegate.mappedStatement");

		RowBounds rowBounds = (RowBounds) metaStatementHandler
				.getValue("delegate.rowBounds");
		if ((rowBounds == null) || (rowBounds == RowBounds.DEFAULT))
			return invocation.proceed();

		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler
				.getValue("delegate.parameterHandler");
		Object parameterObject = statementHandler.getBoundSql()
				.getParameterObject();
		if (parameterObject == null)
			throw new RuntimeException(
					"mybatis Pagination parameterType is null");

		String originalSql = (String) metaStatementHandler
				.getValue("delegate.boundSql.sql");
		if (originalSql == null)
			throw new RuntimeException("get delegate.boundSql.sql is null");
		Configuration configuration = (Configuration) metaStatementHandler
				.getValue("delegate.configuration");

		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables()
					.getProperty("dialect").toUpperCase());
		} catch (Exception localException) {
		}
		if (databaseType == null)
			throw new RuntimeException(
					"the value of the dialect property in configuration.xml is not defined : "
							+ configuration.getVariables().getProperty(
									"dialect"));

		Object dialect = null;
		switch (databaseType) {
		case ORACLE:
			dialect = new OracleDialect();
			break;
		case SQLSERVER:
			dialect = new MySQLDialect();
			break;
		case MYSQL:
			break;
		case DB2:
			dialect = new DB2Dialect();
		}

		String dialectBoundSql = ((Dialect) dialect).getLimitString(
				originalSql, rowBounds.getOffset(), rowBounds.getLimit());
		metaStatementHandler.setValue("delegate.boundSql.sql", dialectBoundSql);
		metaStatementHandler.setValue("delegate.rowBounds.offset",
				Integer.valueOf(0));
		metaStatementHandler.setValue("delegate.rowBounds.limit",
				Integer.valueOf(2147483647));
		if (log.isDebugEnabled()) {
			BoundSql boundSql = statementHandler.getBoundSql();
			log.debug("生成分页SQL : " + boundSql.getSql());
		}

		if (rowBounds instanceof LemonRowBounds) {
			String countSql = "select count(0) from (" + originalSql
					+ ") as totals";
			Connection connection = (Connection) invocation.getArgs()[0];
			if (connection == null)
				throw new RuntimeException(
						"get connection for query count error");
			PreparedStatement countStmt = connection.prepareStatement(countSql);

			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
					countSql, statementHandler.getBoundSql()
							.getParameterMappings(), parameterObject);

			setParameters(countStmt, mappedStatement, countBS, parameterObject);

			ResultSet rs = countStmt.executeQuery();
			int count = 0;
			if (rs.next())
				count = rs.getInt(1);

			rs.close();
			countStmt.close();

			LemonRowBounds iacRowBounds = (LemonRowBounds) rowBounds;
			iacRowBounds.setTotals(count);
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return org.apache.ibatis.plugin.Plugin.wrap(target, this);
	}

	public void setProperties(Properties arg0) {
	}

	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters")
				.object(mappedStatement.getParameterMap().getId());
		List parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = (parameterObject == null) ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); ++i) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings
						.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if ((propertyName.startsWith("__frch_"))
							&& (boundSql.hasAdditionalParameter(prop.getName()))) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null)
							value = configuration.newMetaObject(value)
									.getValue(
											propertyName.substring(prop
													.getName().length()));
					} else {
						value = (metaObject == null) ? null : metaObject
								.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null)
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());

					typeHandler.setParameter(ps, i + 1, value,
							parameterMapping.getJdbcType());
				}
			}
		}
	}

	private String getCountString(String sql) {
		sql = sql.trim();
		sql = sql.toLowerCase();
		int selectLocation = sql.indexOf("select") + 6;
		int fromLocation = sql.indexOf("from");
		StringBuffer pagingSelect = new StringBuffer(sql);
		pagingSelect.replace(selectLocation, fromLocation, " Count(*) ");
		int orderbyLocation = pagingSelect.lastIndexOf("order by");
		return ((orderbyLocation < 0) ? pagingSelect.toString() : pagingSelect
				.substring(0, orderbyLocation).toString());
	}
}