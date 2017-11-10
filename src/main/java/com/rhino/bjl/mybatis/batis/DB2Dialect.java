package com.rhino.bjl.mybatis.batis;

public class DB2Dialect extends Dialect
{
  public String getLimitString(String sql, int offset, int limit)
  {
    return sql;
  }
}