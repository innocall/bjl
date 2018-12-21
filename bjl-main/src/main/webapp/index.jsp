<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/extjs/resources/css/ext-all.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-lang-zh_CN.js"></script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath }/extjs/resources/images/default/s.gif';
        var path = "<c:url value='/'/>";
    </script>
    <title>数据分析平台</title>
</head>
<body style="padding: 0px;margin: 0px;background: #CED9E7">
<script language="javascript" src='<c:url value="./js/login/login.js"/>'></script>
</body>
</html>