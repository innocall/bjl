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
    <SCRIPT>
        function initPrepare1() {
            var number = $('#number').val();
            $.post("login/initPrepare1",{"number":number},function(result){
                alert(result);
            });
        }
    </SCRIPT>
</head>
<body style="padding: 0px;margin: 0px;">
    <div style="width: 100%;margin: 20px;">
        <div style="font-size: 16px;font-weight: 600;">查询第二局结果</div>
        <div style="display: flex;margin-top: 10px;">
            <span>分页单位：</span>
            <input type="number" value="500" id="number">
            <button onclick="initPrepare1();" style="margin-left: 30px;">初始化计算</button>
        </div>
    </div>
</body>
</html>