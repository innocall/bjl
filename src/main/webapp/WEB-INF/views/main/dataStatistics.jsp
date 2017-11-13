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
<body>
    <div style="color: #3366cc;">扑克：8副&nbsp;&nbsp;&nbsp;&nbsp;总共:416张&nbsp;&nbsp;&nbsp;&nbsp;首切牌：10张
        <span style="margin-left: 20px;font-size: 22px;">
            局数：<span id="juCount">0</span>
            &nbsp;&nbsp;&nbsp;&nbsp;庄：<span id="zhuangCount">0</span>
            &nbsp;&nbsp;&nbsp;&nbsp;闲： <span id="xianCount">0</span>
            &nbsp;&nbsp;&nbsp;&nbsp;和：<span id="heCount" >0</span>
            <span id="zhuangDuiCount" style="visibility:hidden;">&nbsp;&nbsp;&nbsp;&nbsp;庄对：0</span>
            <span id="xianDuiCount"style="visibility:hidden;">&nbsp;&nbsp;&nbsp;&nbsp;闲对：0</span>
        </span>
        <span id="msg">请下注</span>
    </div>
    <div style="width: 500px;height: 300px;border: 2px solid #3366cc;margin-top: 7px;position: absolute;">
        <div style="width: 249px;height: 100%;float: left;">
            <span style="background: #0e49e8;font-size: 40px;padding: 5px;font-weight: 800;left: 0px;top: 0px;position: absolute;">闲</span>
            <img id="xian1" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_1.jpg" width="70px;" style="position: relative;top: 70px;left: 55px;visibility:hidden;">
            <img id="xian2" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_9.jpg" width="70px;" style="position: relative;top: 70px;left: 50px;visibility:hidden;">
            <img id="xian3" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_8.jpg" width="70px;" style="position: relative;top: 155px;left: -60px;transform: rotate(90deg);visibility:hidden;">
            <span id="xian" style="background: #333;font-size: 40px;padding: 8px;font-weight: 800;left: 0px;top: 232px;position: absolute;color: #fff;visibility:hidden;">9</span>
        </div>
        <div style="width: 2px;height: 100%;background: #3366cc;float: left"></div>
        <div style="width: 249px;height: 100%;float: right;">
            <span id="endTime" style="background: #333;font-size: 40px;padding: 5px;font-weight: 800;left: 220px;top: 0px;position: absolute;color: #FFFFFF;display: none;width: 50px;text-align: center">30</span>
            <span style="background: #e81131;font-size: 40px;padding: 5px;font-weight: 800;left: 450px;top: 0px;position: absolute;">庄</span>
            <img id="zhuang1" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_1.jpg" width="70px;" style="position: relative;top: 70px;left: 55px;visibility:hidden;">
            <img id="zhuang2" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_3.jpg" width="70px;" style="position: relative;top: 70px;left: 50px;visibility:hidden;">
            <img id="zhuang3" src="${pageContext.request.contextPath}/image/veryhuo.com_pkp_6.jpg" width="70px;" style="position: relative;top: 155px;left: -60px;transform: rotate(90deg);visibility:hidden;">
            <span id="zhuang" style="background: #333;font-size: 40px;padding: 8px;font-weight: 800;left: 459px;top: 232px;position: absolute;color: #fff;visibility:hidden;">9</span>
        </div>
    </div>
    <div style="width: 500px;position: absolute;left: 527px;">
        <table class="table1">
            <tr>
                <th >可用余额</th>
                <th id="userMoney">${paramMap.USER_MONEY}</th>
                <th>
                    <button style="font-size: 20px;padding: 3px;" onclick="chongzhi('${paramMap.USER_ID}',1000000);">充值</button>
                </th>
            </tr>
            <tr>
                <th>投注金额</th>
                <th>0.0</th>
                <th></th>
            </tr>
            <tr>
                <th>庄家抽水</th>
                <th id="clearMoney">${paramMap.ZHUANG_MONEY}</th>
                <th> <button style="font-size: 20px;padding: 3px;" onclick="clearMoney('${paramMap.USER_ID}');">清空抽水</button></th>
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
                    <input type="radio" name="radio" value="0">庄
                    <input type="radio" name="radio" value="1">闲
                    <input type="radio" name="radio" value="2">和
                </th>
            </tr>
            <tr style="margin: 0 auto;height: 50px;">
                <th colspan="3">
                    <button id="touzhuId" style="font-size: 20px;padding: 3px;" >确认投注</button>
                </th>
            </tr>
        </table>
    </div>

    <div style="position: absolute;left: 845px;">
        <div>
            设置时间：  <input id="times" type="number" style="width: 150px;height: 32px;font-size: 20px;" value="${paramMap.TIME}">
            <button id="startBut" style="font-size: 20px;padding: 3px;margin-left: 10px;" onclick="startGame();">开始游戏</button>
        </div>
    </div>

    <!--珠盘路
    1、红、蓝、绿分别表示庄赢、闲赢、和局。
    2、左上角的红点标示表示出现庄对，右下角的蓝点标示表示出现闲对。如果同时出现庄对和闲对，则同时在左上角和右，下角标示红点和蓝点。
    3、通常情况下每列为6个，共11列。
    -->
    <div style="position: absolute;left: 845px;top: 70px;">
        <table class="table3">
            <tr>
                <th>
                    <div class="zhuang">
                        庄
                        <div class="zhuangdu"></div>
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th>
                    <div class="xian">
                        闲
                        <div class="zhuangdu"></div>
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th>
                    <div class="he">
                        和
                    </div>
                </th>
                <th>
                    <img src="${pageContext.request.contextPath}/image/zhang.jpg" width="35px;">
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>
                    <div class="zhuang">
                        庄
                        <div class="zhuangdu"></div>
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th>
                    <div class="zhuang">
                        庄
                    </div>
                </th>
                <th>
                    <div class="zhuang">
                        庄
                    </div>
                </th>
                <th>
                    <div class="zhuang">
                        庄
                    </div>
                </th>
                <th>
                    <div class="xian">
                        闲
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th>
                    <div class="xian">
                        闲
                    </div>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>
                    <div class="zhuang">
                        庄
                    </div>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>
                    <div class="zhuang">
                        庄
                    </div>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>
                    <div class="xian">
                        闲
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>
                    <div class="xian">
                        闲
                        <div class="xiandu"></div>
                    </div>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
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
