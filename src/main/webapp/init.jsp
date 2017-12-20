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
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <title>数据分析平台</title>
    <script>
        function initReet_tbl() {
            $.post("login/reetTbl",function(result){
                alert(result);
            });
        }
        function initRoom_tbl() {
            $.post("login/roomTbl",function(result){
                alert(result);
            });
        }
    </script>
</head>
<body style="padding: 10px;margin: 0px;">
    <button onclick="initReet_tbl();">数据库reet_tbl输赢,奇数偶数初始化初始化</button>
    <button onclick="initRoom_tbl();">数据库room_tbl奇数偶数初始化初始化</button>
    <button>数据库每个房间连庄数，连闲数</button>
