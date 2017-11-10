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
    <meta
            content="width=device-width; initial-scale=1.0; maximum-scale=1.0; minimum-scale=1.0; user-scalable=false;"
            name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/extjs/resources/css/ext-all.css">
    <%-- <link rel="stylesheet" type="text/css" title="gray" href="${pageContext.request.contextPath }/extjs/resources/css/xtheme-gray.css" /> --%>
    <%-- <link rel="stylesheet" type="text/css" title="gray" href="${pageContext.request.contextPath }/extjs/resources/css/xtheme-access.css" /> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/extjs/ext-lang-zh_CN.js"></script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath }/extjs/resources/images/default/s.gif';
        var path = "<c:url value='/'/>";
    </script>
    <style type="text/css">
        .headerDiv{
            background: url(${pageContext.request.contextPath }/image/hd-bg.gif) !important;
            height:23px;
            padding-top:7px;
        }
    </style>
    <title>百家乐数据分析平台</title>
</head>
<body>
<div id="header">
    <div style="color: white;font-family: inherit;font-weight: bold;padding-left: 5px;">
        <%-- <img width="30" height="30"
            src="${pageContext.request.contextPath }/images/bjsi_icon.png" /> --%> 百家乐数据分析平台
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/main/DeskTop.js"></script>
<iframe id="mainFrame" name="mainFrame" frameborder="0" height="100%" width="100%"></iframe>
</body>
</html>