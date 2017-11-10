<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/8
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>百家乐数据分析平台</title>
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta
            content="width=device-width; initial-scale=1.0; maximum-scale=1.0; minimum-scale=1.0; user-scalable=false;"
            name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <style>
        /*1.清除表格默认样式*/
        .table1{
            border-collapse: collapse;
            margin-top: 8px;
        }
        th,td{
            padding: 0;
        }
        /*2.设置表格大小，以及表框颜色*/
        table{
            width: 300px;
        }
        th,td{
            font: 20px/40px "微软雅黑";
            color: #3366cc;
        }
        .table1 th, .table1 td {
            text-align: center;
            border: 2px solid #3366cc;
        }

        .table2{
            border-collapse: collapse;
            margin-top: 8px;
        }

        .table2 th, .table2 td{
            font: 18px/30px "微软雅黑";
            color: #3366cc;
            text-align: center;
            border: 2px solid #3366cc;
        }
    </style>
</head>
<body>
    <div style="color: #3366cc;">扑克：8副&nbsp;&nbsp;&nbsp;&nbsp;总共:416张&nbsp;&nbsp;&nbsp;&nbsp;首切牌：10张&nbsp;&nbsp;&nbsp;&nbsp;尾切牌：78张 </div>
    <div style="width: 500px;height: 300px;border: 2px solid #3366cc;margin-top: 7px;position: absolute;">
        <div style="width: 249px;height: 100%;float: left;">
            <span style="background: #0e49e8;font-size: 40px;padding: 5px;font-weight: 800;left: 0px;top: 0px;position: absolute;">闲</span>
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_1.jpg" width="70px;" style="position: relative;top: 70px;left: 55px;">
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_9.jpg" width="70px;" style="position: relative;top: 70px;left: 50px;">
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_8.jpg" width="70px;" style="position: relative;top: 155px;left: -60px;transform: rotate(90deg);">
            <span style="background: #333;font-size: 40px;padding: 8px;font-weight: 800;left: 0px;top: 232px;position: absolute;color: #fff;">9</span>
        </div>
        <div style="width: 2px;height: 100%;background: #3366cc;float: left"></div>
        <div style="width: 249px;height: 100%;float: right;">
            <span style="background: #e81131;font-size: 40px;padding: 5px;font-weight: 800;left: 450px;top: 0px;position: absolute;">庄</span>
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_1.jpg" width="70px;" style="position: relative;top: 70px;left: 55px;">
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_3.jpg" width="70px;" style="position: relative;top: 70px;left: 50px;">
            <img src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_6.jpg" width="70px;" style="position: relative;top: 155px;left: -60px;transform: rotate(90deg);">
            <span style="background: #333;font-size: 40px;padding: 8px;font-weight: 800;left: 459px;top: 232px;position: absolute;color: #fff;">9</span>
        </div>
    </div>
    <div style="width: 500px;position: absolute;left: 527px;">
        <table class="table1">
            <tr>
                <th >可用余额</th>
                <th>199140.00</th>
                <th>
                    <button style="font-size: 20px;padding: 3px;">充值</button>
                </th>
            </tr>
            <tr>
                <th>投注金额</th>
                <th>0.00</th>
                <th></th>
            </tr>
            <tr>
                <th>庄家抽水</th>
                <th>1010.00</th>
                <th> <button style="font-size: 20px;padding: 3px;">清空抽水</button></th>
            </tr>
        </table>
    </div>
    <div style="width: 500px;position: absolute;left: 520px;top: 190px;">
        <table >
            <tr style="height: 40px;">
                <th >投注金额：</th>
                <th>
                    <input type="number" style="width: 150px;height: 32px;font-size: 20px;" value="0">
                </th>
            </tr>
            <tr style="height: 40px;">
                <th>投注选择：</th>
                <th>
                    <input type="radio" name="radio" value="1">庄
                    <input type="radio" name="radio" value="0">闲
                </th>
            </tr>
            <tr style="margin: 0 auto;height: 50px;">
                <th colspan="3">
                    <button style="font-size: 20px;padding: 3px;">确认投注</button>
                </th>
            </tr>
        </table>
    </div>

    <div style="position: absolute;left:10px;top:370px;">
        <table class="table2" width="460px;" style="width: 460px;">
            <tr>
                <th >扑克</th>
                <th >上限</th>
                <th>盲牌</th>
                <th >明牌</th>
                <th >计算概率</th>
                <th >概率排序</th>
                <th >出现概率</th>
            </tr>
            <tr>
                <th>A</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>2</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>3</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>4</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>5</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>6</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>7</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>8</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>9</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>10</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>J</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>Q</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
            <tr>
                <th>K</th>
                <th>32</th>
                <th>32</th>
                <th>0</th>
                <th>50%</th>
                <th>A</th>
                <th>52%</th>
            </tr>
        </table>
    </div>


</body>
</html>
