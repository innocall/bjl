<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/dataStatistics.css">
    <script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/xmlhttp.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/main/data.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/main/dataStatistics.js"></script>
    <script>
        $(function () {
           init();
        });
    </script>
</head>
<body style="margin: 3px;">
    <div style="color: #3366cc;">扑克：8副&nbsp;&nbsp;共:416张&nbsp;&nbsp;
        首切牌:<span id="startDown">10</span>张
        <span style="margin-left: 12px;font-size: 18px;">
            局数:<span id="juCount" style="color: #ff4545">0</span>
            &nbsp;&nbsp;庄:<span id="zhuangCount" style="color: #ff4545">0</span>
            &nbsp;&nbsp;闲:<span id="xianCount" style="color: #ff4545">0</span>
            &nbsp;&nbsp;和:<span id="heCount"  style="color: #ff4545">0</span>
            &nbsp;&nbsp;庄对:<span id="zhuangDuiCount" style="color: #ff4545">0</span>
            &nbsp;&nbsp;闲对:<span id="xianDuiCount" style="color: #ff4545">0</span>
        </span>
        设置时间: <input id="times" type="number" style="width: 60px;height: 32px;font-size: 20px;" value="${paramMap.TIME}">
        <button id="startBut" style="font-size: 18px;padding: 3px;margin-left: 10px;" onclick="startGame();">开始游戏</button>
        <span id="msg" style="height: 30px;">请下注</span>

    </div>
    <div style="width: 300px;height: 200px;border: 2px solid #3366cc;margin-top: 5px;position: absolute;">
        <div style="width: 149px;height: 100%;float: left;">
            <span style="background: #0e49e8;font-size: 30px;padding: 2px;font-weight: 600;left: 0px;top: 0px;position: absolute;color: #FFF;">闲</span>
            <img id="xian1" src="" width="50px;" style="position: relative;top: 50px;left: 25px;visibility:hidden;">
            <img id="xian2" src="" width="50px;" style="position: relative;top: 50px;left: 20px;visibility:hidden;">
            <img id="xian3" src="" width="50px;" style="position: relative;top: 35px;left: 45px;transform: rotate(90deg);visibility:hidden;">
            <span id="xian" style="background: #333;font-size: 30px;padding: 2px;width:27px;font-weight: 600;left: 0px;top: 155px;position: absolute;color: #fff;visibility:hidden;text-align: center">9</span>
        </div>
        <div style="width: 2px;height: 100%;background: #3366cc;float: left"></div>
        <div style="width: 149px;height: 100%;float: right;">
            <span id="endTime" style="background: #333;font-size: 30px;padding: 5px;font-weight: 600;left: 123px;top: 0px;position: absolute;color: #FFFFFF;display: none;width: 40px;text-align: center">30</span>
            <span style="background: #e81131;font-size: 30px;padding: 2px;font-weight: 600;left: 266px;top: 0px;position: absolute;color: #FFF;">庄</span>
            <img id="zhuang1" src="" width="50px;" style="position: relative;top: 50px;left: 25px;visibility:hidden;">
            <img id="zhuang2" src="" width="50px;" style="position: relative;top: 50px;left: 20px;visibility:hidden;">
            <img id="zhuang3" src="" width="50px;" style="position: relative;top: 35px;left: 45px;transform: rotate(90deg);visibility:hidden;">
            <span id="zhuang" style="background: #333;font-size: 30px;padding: 2px;width:27px;font-weight: 600;left: 270px;top: 155px;position: absolute;color: #fff;visibility:hidden;text-align: center">9</span>
        </div>
    </div>
    <div style="width: 500px;position: absolute;left: 327px;">
        <div style="width: 100%;">
            <table class="table1">
                <tr>
                    <th >可用余额</th>
                    <th id="userMoney">${paramMap.USER_MONEY}</th>
                    <th>
                        <button style="font-size: 16px;padding: 3px;" onclick="chongzhi('${paramMap.USER_ID}',10000);">充值</button>
                    </th>
                </tr>
                <tr>
                    <th>投注金额</th>
                    <th id="touzhuMoney">0.0</th>
                    <th></th>
                </tr>
                <tr>
                    <th>庄家抽水</th>
                    <th id="clearMoney">${paramMap.ZHUANG_MONEY}</th>
                    <th> <button style="font-size: 16px;padding: 3px;" onclick="clearMoney('${paramMap.USER_ID}');">清空抽水</button></th>
                </tr>
            </table>
        </div>
        <div style="width: 100%;margin-top: 6px;">
            <table >
                <tr style="height: 35px;">
                    <th >投注金额：</th>
                    <th>
                        <input id="money" type="number" style="width: 120px;height: 32px;font-size: 20px;" value="0">
                    </th>
                </tr>
                <tr style="height: 35px;">
                    <th>投注选择：</th>
                    <th>
                        <input type="radio" name="radio" value="0" onclick="getValue(this.value)">庄
                        <input type="radio" name="radio" value="1" onclick="getValue(this.value)">闲
                        <input type="radio" name="radio" value="2" onclick="getValue(this.value)">和
                    </th>
                    <th colspan="2">
                        <button id="touzhuId" style="font-size: 16px;padding: 3px;margin-left: 13px;margin-top: -35px;" onclick="touzhu();">确认投注</button>
                    </th>
                </tr>
               <%-- <tr style="margin: 0 auto;height: 50px;">
                    <th colspan="3">
                        <button id="touzhuId" style="font-size: 20px;padding: 3px;" onclick="touzhu();">确认投注</button>
                    </th>
                </tr>--%>
            </table>
        </div>
    </div>

    <div style="position: absolute;left:650px;top:45px;width: 430px;height: 200px;overflow-y: auto; border: 2px solid #3366cc;font-size: 16px;color: #3366cc;">
        <ul id="uls">
          <%--  <li>
                <span>01</span>
                <span style="margin-left: 7px;">庄</span>
                <span style="margin-left: 7px";>庄对</span>
                <span style="margin-left: 7px;">闲对</span>
                <span style="margin-left: 7px;">|</span>
                <span>闲:</span>
                <span>3</span>
                <span style="margin-left: 7px;">K</span>
                <span style="margin-left: 7px;">3</span>
                <span style="margin-left: 7px;">10</span>
                <span style="margin-left: 7px;">|</span>
                <span>庄:</span>
                <span>3</span>
                <span style="margin-left: 7px;">K</span>
                <span style="margin-left: 7px;">3</span>
                <span style="margin-left: 7px;">10</span>
            </li>--%>
        </ul>
    </div>

    <!--大路-->
    <div style="position: absolute;left:10px;top:260px;width: 780px;overflow: auto;height: 150px;">
        <div style="width: 1480px;">
            <table class="table3">
                <%
                     for (int i=1; i<75;i++) {
                %>
                <tr>
                    <%
                        for (int j=1; j<75;j++) {
                    %>
                    <th id="dalu_<%=j + "" + i%>"></th>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>

    <!--珠盘路
   1、红、蓝、绿分别表示庄赢、闲赢、和局。
   2、左上角的红点标示表示出现庄对，右下角的蓝点标示表示出现闲对。如果同时出现庄对和闲对，则同时在左上角和右，下角标示红点和蓝点。
   3、通常情况下每列为6个，共11列。
   -->
    <div style="position: absolute;left: 800px;top: 260px;">
        <table class="table3">
            <tr>
                <th id="img1"></th>
                <th id="img7"></th>
                <th id="img13"></th>
                <th id="img19"></th>
                <th id="img25"></th>
                <th id="img31"></th>
                <th id="img37"></th>
                <th id="img43"></th>
                <th id="img49"></th>
                <th id="img55"></th>
                <th id="img61"></th>
                <th id="img67"></th>
                <th id="img73"></th>
            </tr>
            <tr>
                <th id="img2"> </th>
                <th id="img8"></th>
                <th id="img14"></th>
                <th id="img20"></th>
                <th id="img26"></th>
                <th id="img32"></th>
                <th id="img38"></th>
                <th id="img44"></th>
                <th id="img50"></th>
                <th id="img56"></th>
                <th id="img62"></th>
                <th id="img68"></th>
                <th id="img74"></th>
            </tr>
            <tr>
                <th id="img3"> </th>
                <th id="img9"></th>
                <th id="img15"></th>
                <th id="img21"></th>
                <th id="img27"></th>
                <th id="img33"></th>
                <th id="img39"></th>
                <th id="img45"></th>
                <th id="img51"></th>
                <th id="img57"></th>
                <th id="img63"></th>
                <th id="img69"></th>
                <th id="img75"></th>
            </tr>
            <tr>
                <th id="img4"> </th>
                <th id="img10"></th>
                <th id="img16"></th>
                <th id="img22"></th>
                <th id="img28"></th>
                <th id="img34"></th>
                <th id="img40"></th>
                <th id="img46"></th>
                <th id="img52"></th>
                <th id="img58"></th>
                <th id="img64"></th>
                <th id="img70"></th>
                <th id="img76"></th>
            </tr>
            <tr>
                <th id="img5"> </th>
                <th id="img11"></th>
                <th id="img17"></th>
                <th id="img23"></th>
                <th id="img29"></th>
                <th id="img35"></th>
                <th id="img41"></th>
                <th id="img47"></th>
                <th id="img53"></th>
                <th id="img59"></th>
                <th id="img65"></th>
                <th id="img71"></th>
                <th id="img77"></th>
            </tr>
            <tr>
                <th id="img6"> </th>
                <th id="img12"></th>
                <th id="img18"></th>
                <th id="img24"></th>
                <th id="img30"></th>
                <th id="img36"></th>
                <th id="img42"></th>
                <th id="img48"></th>
                <th id="img54"></th>
                <th id="img60"></th>
                <th id="img66"></th>
                <th id="img72"></th>
                <th id="img78"></th>
            </tr>
        </table>
    </div>

    <!--大眼仔-->
    <div style="position: absolute;left:10px;top:420px;width: 370px;overflow: auto;height: 150px;">
        <div style="width: 1480px;">
            <table class="table3">
                <%
                    for (int i=1; i<75;i++) {
                %>
                <tr>
                    <%
                        for (int j=1; j<75;j++) {
                    %>
                    <th id="dayanzai_<%=j + "" + i%>"></th>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>

    <!--小路-->
    <div style="position: absolute;left:380px;top:420px;width: 370px;overflow: auto;height: 150px;">
        <div style="width: 1480px;">
            <table class="table3">
                <%
                    for (int i=1; i<75;i++) {
                %>
                <tr>
                    <%
                        for (int j=1; j<75;j++) {
                    %>
                    <th id="xiaolu_<%=j + "" + i%>"></th>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>

    <!--蟑螂路-->
    <div style="position: absolute;left:750px;top:420px;width: 370px;overflow: auto;height: 150px;">
        <div style="width: 1480px;">
            <table class="table3">
                <%
                    for (int i=1; i<75;i++) {
                %>
                <tr>
                    <%
                        for (int j=1; j<75;j++) {
                    %>
                    <th id="zhanglanglu_<%=j + "" + i%>"></th>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>


    <div id="totle" style="position: absolute;top: 160px;left: 200px; background:#00000000;text-align: center;z-index: 999;visibility:hidden;">
        <img id="qie1" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_1.jpg" width="80;" style="margin: 0 auto;">
        <img id="qie2" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie3" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie4" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie5" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie6" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie7" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie8" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie9" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
        <img id="qie10" src="${pageContext.request.contextPath}/image/pukepaifanm.jpg" width="80;" style="visibility:hidden;">
    </div>

</body>
</html>
