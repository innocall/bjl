<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/8
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta content="width=device-width; initial-scale=1.0; maximum-scale=1.0; minimum-scale=1.0; user-scalable=false;" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/extjs/resources/css/ext-all.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ux/ProgressBarPager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/main/data.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/data/index.js"></script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath }/extjs/resources/images/default/s.gif';
        var path = "<c:url value='/'/>";
    </script>
    <title>百家乐数据分析平台</title>
    <style>
        .row-wylevel1 {
            color: red !important;
        }
        .x-selectable, .x-selectable * {
            -moz-user-select: text! important ;
            -khtml-user-select: text! important ;
        }
    </style>
</head>
<body>
</body>
</html>
