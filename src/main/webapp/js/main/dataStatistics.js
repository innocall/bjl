//开始游戏
var time = 30;
var isStart = 0;
var mytime=null;
var isFaPai = false;
var juCount = 0;
var zhuangCount = 0;
var xianCount = 0;
var heCount = 0;
var zhuangDuiCount1 = 0;
var xianDuiCount1 = 0;
var myArray=new Array()
var xiandui = 1; //闲没有对子1 ，0有对子
var zhuangdui = 1; //庄没有对子1 ，0有对子
//首切牌随机 8-9-10

var startDown=new Array(8,9,10);
// var allJuCountArray=new Array(70,71,72,73,74,75);
 var allJuCount=75; //最多不超过75局
var firstQiePai = "";
var startDown1 = 0; //首切牌张数
var isJieShu = false; //是否结束发到了红牌，true 发到了红牌，结束
function init() {
    isStart = 0;
    isOne = true;
    mytime=null;
    isFaPai = false;
    juCount = 0;
    zhuangCount = 0;
    xianCount = 0;
    heCount = 0;
    zhuangDuiCount1 = 0;
    xianDuiCount1 = 0;
    xiandui = 1;
    zhuangdui = 1;
    roomId = "";
    isJieShu = false;
    $("#juCount").html(juCount);
    $("#zhuangCount").html(zhuangCount);
    $("#xianCount").html(xianCount);
    $("#heCount").html(heCount);
    $("#zhuangDuiCount").html(zhuangDuiCount1);
    $("#xianDuiCount").html(xianDuiCount1);
    $(".zhupanlu").remove();
    $("#xian").css("visibility","hidden");
    $("#xian1").css("visibility","hidden");
    $("#xian2").css("visibility","hidden");
    $("#xian3").css("visibility","hidden");
    $("#zhuang").css("visibility","hidden");
    $("#zhuang1").css("visibility","hidden");
    $("#zhuang2").css("visibility","hidden");
    $("#zhuang3").css("visibility","hidden");

    //初始化扑克数组
    for (var i=0;i<416;i++) {
        myArray[i] = i + 1;
    }
    showLog(myArray);
    myArray = shuffleArray(myArray); //洗牌
    showLog(myArray);
   // alert(myArray[0]);
    //去掉首切牌10张
    //首切牌随机 8-9-10 张
    //总局数，随机 70-75
    var index = Math.floor((Math.random() * startDown.length));
    startDown1 = startDown[index];
    $("#startDown").html(startDown1);
    //首切牌，切掉上面的几张牌
    for (var j =0; j<startDown1;j++) {
        if (j == 0) {
            //var index = Math.floor((Math.random() * myArray.length));
            firstQiePai = "veryhuo.com_pkp_" + myArray[j] + ".jpg"; //记录切的第一张牌
           // myArray.remove(index);
        }
        myArray = removArrayByIndex(myArray,0);
        //showLog(myArray);
    }
    showLog(myArray);
    //尾切牌
    var lanrenzhijia = Math.floor(Math.random()*(120-60)+ 60);
    //插入红牌
    myArray = downSet(myArray,lanrenzhijia);
    showLog(myArray);
    $("#endDown").text(lanrenzhijia);
    //var index2 = Math.floor((Math.random() * allJuCountArray.length));
   // allJuCount = allJuCountArray[index2];
   // $("#juCount").html(allJuCount);
}

function random() {
    var index = Math.floor((Math.random() * myArray.length));
    myArray = removArrayByIndex(myArray,index);
   // myArray.remove(index);
    return index;
}

var isOne = true;

function startGame() {
    if (isStart == 0) {
        time = $("#times").val();
        if (time <= 0) {
            alert("时间不能为负数或0");
            return;
        }
        $("#startBut").html("暂停游戏");
        $("#endTime").html(time);
        $("#endTime").css("display","block");
        $("#msg").html("请下注");
        $("#msg").css("display","initial");
        $("#touzhuId").removeAttr("disabled");
        isStart = 1;
        if (isOne) {
            showTotal();
            isOne = false;
        }
        settime();
    } else if (isStart == 1){
        if (!isFaPai) {
            $("#startBut").html("继续游戏");
            isStart = 2
            doPause();
        } else {
            alert("游戏开始不能暂停");
        }
    } else if (isStart == 2) {
        $("#startBut").html("暂停游戏");
        doGo();
        isStart = 1;
    }
}

function settime() {
    if (time == 0 || time < 0) {
        //发牌
        $("#msg").html("暂停投注，发牌中");
        $("#touzhuId").attr("disabled", "disabled");
        $("#endTime").css("display","none");
        isFaPai = true;
        faPai1();
    } else {
        time--;
        $("#endTime").html(time);
        mytime = setTimeout(function() {settime() },1000);
    }
}

//暂停倒计时
function doPause(){
    if(mytime!=null){
        clearTimeout(mytime);
        mytime=null;
    }
}

//继续倒计时
function doGo(){
    settime();
}

function getCount(allCount) {
    var count = allCount;
    if (allCount > 9) {
        count = allCount%10;
    }
    return count;
}

/**
 * 开始发牌
 * 第一及第三张牌发给“闲家”，第二及第四张牌则发给“庄家”
 */
function faPai1() {
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
    //myArray.remove(index);
    $("#xian1").attr("src","/bjl/image/" + imageName);
    $("#xian1").css("visibility","visible");
    var xian1 = initData(imageName); //第一张牌点数
    $("#xian").html(showData(xian1));
    $("#xian").css("visibility","visible");
    setTimeout(function() {faPai2(xian1) },1000);
}

function faPai2(xian1) {
    //庄第一张
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
    //myArray.remove(index);
    $("#zhuang1").attr("src","/bjl/image/" + imageName);
    $("#zhuang1").css("visibility","visible");
    var zhuang1 = initData(imageName); //第一张牌点数
    $("#zhuang").html(showData(zhuang1));
    $("#zhuang").css("visibility","visible");
    setTimeout(function() {faPai3(xian1,zhuang1) },1000);
}

function faPai3(xian1,zhuang1) {
    //闲第二张
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
    //myArray.remove(index);
    $("#xian2").attr("src","/bjl/image/" + imageName);
    $("#xian2").css("visibility","visible");
    var xian2 = initData(imageName); //第一张牌点数
    $("#xian").html(getCount(showData(xian1) +showData( xian2)));
    $("#xian").css("visibility","visible");
    setTimeout(function() {faPai4(xian1,zhuang1,xian2) },1000);
}

function faPai4(xian1,zhuang1,xian2) {
    //庄第二张
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
    //myArray.remove(index);
    $("#zhuang2").attr("src","/bjl/image/" + imageName);
    $("#zhuang2").css("visibility","visible");
    var zhuang2 = initData(imageName); //第一张牌点数
    var zhuang = getCount(showData(zhuang1) + showData(zhuang2));
    $("#zhuang").html(zhuang);
    $("#zhuang").css("visibility","visible");
    //判断是否有对子
    if(xian1 == xian2) {
        xiandui = 0;
    }
    if(zhuang1 == zhuang2) {
        zhuangdui = 0;
    }
    //判断是否需要博牌
    var xian = getCount(showData(xian1) +showData(xian2));
    if (xian ==9 || xian ==8 || zhuang == 9 || zhuang == 8 ) {
        goGame(xian1,zhuang1,xian2,zhuang2,0,0);
    } else {
        if ( xian < 6 ) {
            //闲需要博牌
            setTimeout(function() {faPaiXian(xian1,zhuang1,xian2,zhuang2) },1000);
        } else if (xian == 6 || xian == 7) {
            if (zhuang < 6) {
                setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,0) },1000);
            } else {
                goGame(xian1,zhuang1,xian2,zhuang2,0,0);
            }
        }
    }
}

function faPaiXian(xian1,zhuang1,xian2,zhuang2) {
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
   // myArray.remove(index);
    $("#xian3").attr("src","/bjl/image/" + imageName);
    $("#xian3").css("visibility","visible");
    var xian3 = initData(imageName); //第一张牌点数
    $("#xian").html(getCount(showData(xian1) + showData(xian2) + showData(xian3)));
    $("#xian").css("visibility","visible");
    var zhuang =  getCount(showData(zhuang1) + showData(zhuang2));
    if(xian1 == xian2 || xian1 == xian3 || xian2 == xian3) {
        xiandui = 0;
    }
    if (zhuang ==  0 || zhuang ==  1 || zhuang ==  2) {
        setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
    } else if (zhuang == 3) {
        if (xian3 != 8) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else {
            goGame(xian1,zhuang1,xian2,zhuang2,xian3,0);
        }
    } else if (zhuang == 4) {
        if (xian3 != 0) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 1) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 8) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 9) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else {
            goGame(xian1,zhuang1,xian2,zhuang2,xian3,0);
        }
    } else if (zhuang == 5) {
        if (xian3 != 0) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 1) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 2) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 3) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 8) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 != 9) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else {
            goGame(xian1,zhuang1,xian2,zhuang2,xian3,0);
        }
    } else if (zhuang == 6) {
        if (xian3 == 6) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else if (xian3 == 7) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) },1000);
        } else {
            goGame(xian1,zhuang1,xian2,zhuang2,xian3,0);
        }
    } else {
        goGame(xian1,zhuang1,xian2,zhuang2,xian3,0);
    }
}

function faPaiZhuang(xian1,zhuang1,xian2,zhuang2,xian3) {
    //var index = Math.floor((Math.random() * myArray.length));
    var index = 0;
    if (myArray[index] == -1) {
        isJieShu = true; //红牌
        $("#jieshu").css("visibility","visible");
        myArray = removArrayByIndex(myArray,index);
    }
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray = removArrayByIndex(myArray,index);
    //myArray.remove(index);
    $("#zhuang3").attr("src","/bjl/image/" + imageName);
    $("#zhuang3").css("visibility","visible");
    var zhuang3 = initData(imageName);
    $("#zhuang").html(getCount(showData(zhuang1) + showData(zhuang2) + showData(zhuang3)));
    $("#zhuang").css("visibility","visible");
    //判断是否有对子
    if(zhuang1 == zhuang2 || zhuang1 == zhuang3 || zhuang2 == zhuang3) {
        zhuangdui = 0;
    }
    goGame(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3);
}

/*function zhuang() {
    goGame('12','2','12','4','','');
}

function xian() {
    goGame('2','12','2','12','','');
}

function heco() {
    goGame('2','2','2','2','2','2');
}*/

var shuyingqian = 0; //记录输赢钱数
function goGame(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3) {
    //提交数据到后台
    juCount = juCount + 1;
    $("#juCount").html(juCount);
    var zhuangdian = getCount(showData(zhuang1) + showData(zhuang2) + showData(zhuang3)); //庄点数
    var xiandian = getCount(showData(xian1) + showData(xian2) + showData(xian3));  //闲点数
    //设置珠盘路
    setZhuPanLu(zhuangdian,xiandian);
    //设置大路
    setDaLu(zhuangdian,xiandian);
    //设置大眼路
    setDaYanLu(zhuangdian,xiandian);
    //设置小路
    setXiaoLu(zhuangdian,xiandian);
    //设置蟑螂路
    setZhangLangLu(zhuangdian,xiandian);
    var userMoney =parseFloat($("#userMoney").text());
    var touzhuMoney =parseFloat($("#touzhuMoney").text());
    var clearMoney = parseFloat($("#clearMoney").text());
    $("#touzhuMoney").text("0.0")
    if (zhuangdian > xiandian) {
        //庄赢
        var choushui = (touzhuMoney * 0.05);
        $("#clearMoney").html((clearMoney + choushui).toFixed(2))
        zhuangCount = zhuangCount + 1;
        $("#zhuangCount").html(zhuangCount);
        $("#msg").html("庄赢，结算中");
        if (radio == 0) {
            shuyingqian = toDecimal(shuyingqian + (touzhuMoney - choushui));
            $("#userMoney").text((userMoney + touzhuMoney + (touzhuMoney - choushui)).toFixed(2))
        } else {
            shuyingqian = toDecimal(shuyingqian - touzhuMoney);
            $("#userMoney").text(userMoney.toFixed(2) );
        }
    } else if (zhuangdian < xiandian) {
        //闲赢
        xianCount = xianCount + 1;
        $("#xianCount").html(xianCount);
        $("#msg").html("闲赢，结算中");
        if (radio == 1) {
            shuyingqian = toDecimal(shuyingqian + touzhuMoney);
            $("#userMoney").text((userMoney + touzhuMoney + touzhuMoney ).toFixed(2))
        } else {
            shuyingqian = toDecimal(shuyingqian - touzhuMoney);
            $("#userMoney").text(userMoney.toFixed(2) );
        }
    } else {
        //和
        heCount = heCount + 1;
        $("#heCount").html(heCount);
        $("#msg").html("和，结算中");
        if (radio == 2) {
            shuyingqian = toDecimal(shuyingqian + touzhuMoney);
            $("#userMoney").text((userMoney + touzhuMoney + touzhuMoney ).toFixed(2))
        } else {
            shuyingqian = toDecimal(shuyingqian - touzhuMoney);
            $("#userMoney").text(userMoney .toFixed(2) );
        }
    }
    if (zhuangdui == 0) {
        zhuangDuiCount1 = zhuangDuiCount1 + 1;
        $("#zhuangDuiCount").html(zhuangDuiCount1);
        zhuangdui = 1;
    }
    if (xiandui == 0) {
        xianDuiCount1 = xianDuiCount1 + 1;
        $("#xianDuiCount").html(xianDuiCount1);
        xiandui = 1;
    }
    $("#shuying").html(shuyingqian);
    submitDate(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3,userMoney,touzhuMoney,zhuangdian,xiandian);
}

function setZhuPanLu(zhuangdian,xiandian) {
    //<img class="zhupanlu" src="../../image/zhang.jpg" width="30px;">
    if (zhuangdian > xiandian) {
        //庄赢
        if(zhuangdui == 0){
            //庄队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/zhuangdui.png\" width=\"20px;\">");
        } else if(xiandui == 0){
            //闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/zhuangxiandui.png\" width=\"20px;\">");
        } else if (zhuangdui == 0 && xiandui == 0) {
            //庄队，闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/zhuangd.png\" width=\"20px;\">");
        } else {
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/zhuang.png\" width=\"20px;\">");
        }
    } else if (zhuangdian < xiandian) {
        //闲赢
        if(zhuangdui == 0){
            //庄队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/xianzhuangdui.png\" width=\"20px;\">");
        } else if(xiandui == 0){
            //闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/xiandui.png\" width=\"20px;\">");
        } else if (zhuangdui == 0 && xiandui == 0) {
            //庄队，闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/zhuangd.png\" width=\"20px;\">");
        } else {
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/xian.png\" width=\"20px;\">");
        }
    } else {
        //和
        if(zhuangdui == 0){
            //庄队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/hedui.png\" width=\"20px;\">");
        } else if(xiandui == 0){
            //闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/hezhuangdui.png\" width=\"20px;\">");
        } else if (zhuangdui == 0 && xiandui == 0) {
            //庄队，闲队
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/hed.png\" width=\"20px;\">");
        } else {
            $("#img" + juCount +"").html("<img class=\"zhupanlu\" src=\"../../image/he.png\" width=\"20px;\">");
        }
    }
}

var cloun = 1;//记录大路到第几列
var row = 1; //记录大路到第几行
var up = 0;  //记录上一局是 1 庄 2 闲 3 和
var he = 0; //记录和的条数
var daLuLen=new Array(); //记录大路每一列长度
/**
 * 大眼路规则
 *  1、大路每列的第1粒：比较前面两列的个数，前面两列齐脚是红笔，前面两列不齐脚是蓝笔。
 *  2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。
 *     红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔，注：此名称是我自己命名的
 */
function setDaLu(zhuangdian,xiandian) {
    if (zhuangdian > xiandian) {
        //庄赢
        if (he > 0 && row == 1) {
            $("#dalu_" + cloun + "" + row +"").children("div").html(he);
        }
        if (up == 0) {
            $("#dalu_" + cloun + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        } else if (up == 1) {
            row = row + 1
            //判断下一行是否有写路
           /* if ($("#dalu_" + cloun  + "" +row +":has(div)" ).length==0){
                //不存在
                if (row > 6) {
                    $("#dalu_" + (cloun + (row - 6))  + "6").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
                } else {
                    $("#dalu_" + cloun  + "" +row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else{
                //已经写路
                $("#dalu_" + (cloun + (6 - (row-1)))  + (row - 1) + "").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
            }*/
            $("#dalu_" + cloun  + "" +row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        } else if (up == 2) {
            daLuLen[daLuLen.length] = row;
            he = 0;
            row = 1;
            cloun = cloun + 1
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        }
       /* else if (up == 3) {
            daLuLen[daLuLen.length] = row;
            row = 1;
            cloun = cloun + 1
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #ff4545;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        }*/
        up = 1;
    } else if (zhuangdian < xiandian) {
        //闲赢
        if (he > 0 && row == 1) {
            $("#dalu_" + cloun + "" + row +"").children("div").html(he);
        }
        if (up == 0) {
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        } else if (up == 1) {
            daLuLen[daLuLen.length] = row;
            he = 0;
            row = 1;
            cloun = cloun + 1
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        } else if (up == 2) {
            row = row + 1
            //判断下一行是否有写路
           /* if ($("#dalu_" + cloun  + "" +row +":has(div)" ).length==0){
                //不存在
                if (row > 6) {
                    $("#dalu_" + (cloun + (row - 6))  + "6").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
                } else {
                    $("#dalu_" + cloun  + "" +row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else{
                //已经写路
                $("#dalu_" + (cloun + (6 - (row-1)))  + (row - 1) + "").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
            }*/
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        }
      /*  else if (up == 3) {
            daLuLen[daLuLen.length] = row;
            row = 1;
            cloun = cloun + 1
            $("#dalu_" + cloun  + "" + row +"").html("<div class=\"dulu\" style=\"width: 14px;height: 14px;border: 2px solid #0c41ff;border-radius: 20px;margin: 0 auto;line-height: 11px;\"></div>");
        }*/
        up = 2;
    } else {
        //和
        he = he + 1;
        if (up != 0) {
            $("#dalu_" + cloun + "" + row +"").children("div").html(he);
        }
       // up = 3;
    }
}

var daCloun = 1;//记录大眼路到第几列
var daRow = 1; //记录大眼路到第几行
var daUp = 0; //记录大眼路上一个颜色 0 第一次写，1 红色 2蓝色
function  setDaYanLu(zhuangdian,xiandian) {
    if (zhuangdian != xiandian) {
        if (row == 1) {
            if (cloun > 2) {
                var cloum1 = cloun - 1;
                var row1 = daLuLen[cloum1-1];
                var cloum2 = cloun - 2;
                var row2 = daLuLen[cloum2-1];
                if (row1 == row2) {
                    if(daUp == 0) {
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 1) {
                        daRow = daRow + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 2) {
                        daRow = 1;
                        daCloun = daCloun + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    daUp = 1;
                } else {
                    if(daUp == 0) {
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 1) {
                        daRow = 1;
                        daCloun = daCloun + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 2) {
                        daRow = daRow + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    daUp = 2;
                }
            }
        } else {
            //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
            if (cloun > 1) {
                var cloum1 = cloun - 1;
                var row1 = daLuLen[cloum1 - 1];  //上一列的行数
                //alert(row1 + ":" + row + ":" + Math.abs(row1 - row));
                if (row - row1 == 1) {
                    if(daUp == 0) {
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 1) {
                        daRow = 1;
                        daCloun = daCloun + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 2) {
                        daRow = daRow + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    daUp = 2;
                } else {
                    //成对，红
                    if(daUp == 0) {
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 1) {
                        daRow = daRow + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (daUp == 2) {
                        daRow = 1;
                        daCloun = daCloun + 1;
                        $("#dayanzai_" + daCloun + "" + daRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    daUp = 1;
                }
            }
        }
    }
}

var xiaoCloun = 1;//记录小路到第几列
var xiaoRow = 1; //记录小路到第几行
var xiaoUp = 0; //记录小路上一个颜色 0 第一次写，1 红色 2蓝色
function  setXiaoLu(zhuangdian,xiandian) {
    if (zhuangdian != xiandian) {
        if (row == 1) {
            if (cloun > 3) {
                var cloum1 = cloun - 1;
                var row1 = daLuLen[cloum1-1];
                var cloum2 = cloun - 3;
                var row2 = daLuLen[cloum2-1];
                if (row1 == row2) {
                    if(xiaoUp == 0) {
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 1) {
                        xiaoRow = xiaoRow + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 2) {
                        xiaoRow = 1;
                        xiaoCloun = xiaoCloun + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    xiaoUp = 1;
                } else {
                    if(xiaoUp == 0) {
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 1) {
                        xiaoRow = 1;
                        xiaoCloun = xiaoCloun + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 2) {
                        xiaoRow = xiaoRow + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    xiaoUp = 2;
                }
            }
        } else {
            //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
            if (cloun > 2) {
                var cloum1 = cloun - 2;
                var row1 = daLuLen[cloum1 - 1];  //上一列的行数
                //alert(row1 + ":" + row + ":" + Math.abs(row1 - row));
                if (row - row1 == 1) {
                    if(xiaoUp == 0) {
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 1) {
                        xiaoRow = 1;
                        xiaoCloun = xiaoCloun + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 2) {
                        xiaoRow = xiaoRow + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    xiaoUp = 2;
                } else {
                    //成对，红
                    if(xiaoUp == 0) {
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 1) {
                        xiaoRow = xiaoRow + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    } else if (xiaoUp == 2) {
                        xiaoRow = 1;
                        xiaoCloun = xiaoCloun + 1;
                        $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                    }
                    xiaoUp = 1;
                }
            }
        }
    }
}

var zhangCloun = 1;//记录小路到第几列
var zhangRow = 1; //记录小路到第几行
var zhangUp = 0; //记录小路上一个颜色 0 第一次写，1 红色 2蓝色
function  setZhangLangLu(zhuangdian,xiandian) {
    if (zhuangdian != xiandian) {
        if (row == 1) {
            if (cloun > 4) {
                var cloum1 = cloun - 1;
                var row1 = daLuLen[cloum1-1];
                var cloum2 = cloun - 4;
                var row2 = daLuLen[cloum2-1];
                if (row1 == row2) {
                    if(zhangUp == 0) {
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    } else if (zhangUp == 1) {
                        zhangRow = zhangRow + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    } else if (zhangUp == 2) {
                        zhangRow = 1;
                        zhangCloun = zhangCloun + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    }
                    zhangUp = 1;
                } else {
                    if(zhangUp == 0) {
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    } else if (zhangUp == 1) {
                        zhangRow = 1;
                        zhangCloun = zhangCloun + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    } else if (zhangUp == 2) {
                        zhangRow = zhangRow + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    }
                    zhangUp = 2;
                }
            }
        } else {
            //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
            if (cloun > 3) {
                var cloum1 = cloun - 3;
                var row1 = daLuLen[cloum1 - 1];  //上一列的行数
                //alert(row1 + ":" + row + ":" + Math.abs(row1 - row));
                if (row - row1 == 1) {
                    if(zhangUp == 0) {
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    } else if (zhangUp == 1) {
                        zhangRow = 1;
                        zhangCloun = zhangCloun + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    } else if (zhangUp == 2) {
                        zhangRow = zhangRow + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                    }
                    zhangUp = 2;
                } else {
                    //成对，红
                    if(zhangUp == 0) {
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    } else if (zhangUp == 1) {
                        zhangRow = zhangRow + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    } else if (zhangUp == 2) {
                        zhangRow = 1;
                        zhangCloun = zhangCloun + 1;
                        $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                    }
                    zhangUp = 1;
                }
            }
        }
    }
}

/**
 * 提交
 * @param xian1
 * @param zhuang1
 * @param xian2
 * @param zhuang2
 * @param xian3
 * @param zhuang3
 * @param userMoney
 * @param touzhuMoney
 */
var roomId;
function submitDate(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3,userMoney,touzhuMoney,zhuangdian,xiandian) {
    //显示数据
    var zhuangdui1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    var xiandui1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    var jieguo1 = "&nbsp;&nbsp;&nbsp;";
    var ying = ""; //记录投注输赢，空未投注
    if(zhuang1 == zhuang2 || zhuang1 == zhuang3 || zhuang2 == zhuang3) {
        zhuangdui1 = "庄对";
    }
    if(xian1 == xian2 || xian1 == xian3 || xian2 == xian3) {
        xiandui1 = "闲对";
    }
    if (zhuangdian == xiandian) {
        jieguo1 = "和";
        if (radio != -1){
            if (radio == 2) {
                ying = "赢"
            } else{
                ying = "输"
            }
        }
    } else if (zhuangdian > xiandian) {
        jieguo1 = "庄";
        if (radio != -1){
            if (radio == 0) {
                ying = "赢"
            } else{
                ying = "输"
            }
        }
    } else if (zhuangdian < xiandian) {
        jieguo1 = "闲";
        if (radio != -1){
            if (radio == 1) {
                ying = "赢"
            } else{
                ying = "输"
            }
        }
    }
    //记录日志
    showLog(myArray);
    if (juCount == 1) {
        $("#uls").html("<li>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;font-size: 13px;line-height: 23px;\">"+ juCount +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center\">"+ jieguo1 +"</div>\n" +
            "                <div style=\"margin-left: 7px; float: left;width: 40px;text-align: center\">"+ zhuangdui1 +"</div>\n" +
            "                <div style=\"margin-left: 7px; float: left;width: 40px;text-align: center\">"+ xiandui1 +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 7px;\">|</div>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;height: 21px;\">闲:</div>\n" +
            "                <div style=\"float: left;width: 15px;text-align: center;height: 21px;\">"+ xiandian +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">" + showData2(xian3) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(xian1) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(xian2) +"</div>\n" +
            "                <div style=\"margin-left: 13px;float: left;width: 7px;text-align: center;height: 21px;\">|</div>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;height: 21px;\">庄:</div>\n" +
            "                <div style=\"float: left;width: 15px;text-align: center;height: 21px;\">"+ zhuangdian +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang3) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang1) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang2) +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">"+ ying +"</div>\n" +
            "            </li>");
    } else {
        $("ul:last").after("<ul style=\"clear: both\"><li>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;font-size: 13px;line-height: 23px;\">"+ juCount +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center\">"+ jieguo1 +"</div>\n" +
            "                <div style=\"margin-left: 7px; float: left;width: 40px;text-align: center\">"+ zhuangdui1 +"</div>\n" +
            "                <div style=\"margin-left: 7px; float: left;width: 40px;text-align: center\">"+ xiandui1 +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 7px;text-align: center\">|</div>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;height: 21px;\">闲:</div>\n" +
            "                <div style=\"float: left;width: 15px;text-align: center;height: 21px;\">"+ xiandian +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">" + showData2(xian3) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(xian1) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(xian2) +"</div>\n" +
            "                <div style=\"margin-left: 13px;float: left;width: 7px;text-align: center;height: 21px;\">|</div>\n" +
            "                <div style=\"float: left;width: 20px;text-align: center;height: 21px;\">庄:</div>\n" +
            "                <div style=\"float: left;width: 15px;text-align: center;height: 21px;\">"+ zhuangdian +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang3) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang1) +"</div>\n" +
            "                <div style=\"margin-left: 7px;float: left;width: 15px;text-align: center;height: 21px;\">"+ showData2(zhuang2) +"</div>\n" +
            "                <div style=\"margin-left: 9px;float: left;width: 15px;text-align: center;height: 21px;\">"+ ying +"</div>\n" +
            "            </li></ul>");
        document.getElementById("lists").scrollTop =  document.getElementById("lists").scrollHeight;
    }
    $.post("submitDate",{
            xian1:xian1,
            zhuang1:zhuang1,
            xian2:xian2,
            zhuang2:zhuang2,
            xian3:xian3,
            zhuang3:zhuang3,
            userMoney:userMoney,
            touzhuMoney:touzhuMoney,
            juCount:juCount,
            zhuangCount:zhuangCount,
            xianCount:xianCount,
            heCount:heCount,
            zhuangDuiCount1:zhuangDuiCount1,
            xianDuiCount:xianDuiCount1,
            zhuangdian:zhuangdian,
            xiandian:xiandian,
            radio:radio,
            roomId:roomId
        },
        function(data,textStatus){
            var obj = eval('(' + data + ')');
            var status = obj.status;
            if(status == 200) {
                roomId = obj.msg;
               // alert(obj.msg);
                if (juCount < allJuCount && !isJieShu) {
                    setTimeout(function() {goOnGame() },5000);
                } else {
                    $("#msg").html("游戏结束");
                    $("#msg").css("display","initial");
                }
            } else {
                alert("游戏保存失败，请刷新页面重试");
            }
            //alert(obj.msg);
        }
    );

}

function goOnGame() {
    isFaPai = false;
    isStart = 0;
    //$("#money").val(0);
    radio = -1;
    $(".radio").removeAttr("checked");
    $("#xian1").css("visibility","hidden");
    $("#xian2").css("visibility","hidden");
    $("#xian3").css("visibility","hidden");
    $("#xian").css("visibility","hidden");
    $("#zhuang1").css("visibility","hidden");
    $("#zhuang2").css("visibility","hidden");
    $("#zhuang3").css("visibility","hidden");
    $("#zhuang").css("visibility","hidden");
    startGame();
}

function chongzhi(userId,money) {
    var value = prompt('输入充值金额：', '');
    if(value == ''||value == null){
        alert('输入为空');
        return;
    } else{
        var userMoney =parseFloat($("#userMoney").text());
        money = userMoney + parseFloat(value);
    }
    $.post("chongzhi",{
            id:userId,
            money:money
        },
        function(data,textStatus){
            var obj = eval('(' + data + ')');
            var status = obj.status;
            if(status == 200) {
                $("#userMoney").html(money);
            }
            alert(obj.msg);
        }
    );
}

function clearMoney(userId) {
    $.post("clearMoney",{
            id:userId
        },
        function(data,textStatus){
            var obj = eval('(' + data + ')');
            var status = obj.status;
            if(status == 200) {
                $("#clearMoney").html("0.0");
            }
            alert(obj.msg);
        }
    );
}

var radio = -1;
/**
 * 投注
 */
function touzhu() {
    var money = parseFloat($("#money").val());
    var userMoney =parseFloat($("#userMoney").text());
    var touzhuMoney =parseFloat($("#touzhuMoney").text());
    radio = getValue();
    if (money <= 0 || money == null || money == NaN) {
        alert("请输入投注金额");
        return;
    }
    if (money > userMoney) {
        alert("可用余额为：" + userMoney);
        return;
    }
    if (radio == -1) {
        alert("请选择投注方" );
        return;
    }
    $("#touzhuMoney").html((money + touzhuMoney).toFixed(2));
    $("#userMoney").html((userMoney - money).toFixed(2));
}

function showTotal() {
    $("#totle").css("visibility","visible");
    $("#qie1").attr("src","/bjl/image/" + firstQiePai);
    var time = 0;
    for (var i=1;i<startDown1 + 1;i++) {
        time = time + 600;
        if (i == 1) {
            setTimeout(function() {
                $("#qie1").css("visibility","visible")
            },time)
        } else if (i == 2) {
            setTimeout(function() {
                $("#qie2").css("visibility","visible")
            },time)
        } else if (i == 3) {
            setTimeout(function() {
                $("#qie3").css("visibility","visible")
            },time)
        } else if (i == 4) {
            setTimeout(function() {
                $("#qie4").css("visibility","visible")
            },time)
        } else if (i == 5) {
            setTimeout(function() {
                $("#qie5").css("visibility","visible")
            },time)
        } else if (i == 6) {
            setTimeout(function() {
                $("#qie6").css("visibility","visible")
            },time)
        } else if (i == 7) {
            setTimeout(function() {
                $("#qie7").css("visibility","visible")
            },time)
        } else if (i == 8) {
            setTimeout(function() {
                $("#qie8").css("visibility","visible")
                if (8 == startDown1) {
                    hiddenTotal();
                }
            },time)
        } else if (i == 9) {
            setTimeout(function() {
                $("#qie9").css("visibility","visible")
                if (9 == startDown1) {
                    hiddenTotal();
                }
            },time)
        } else if (i == 10) {
            setTimeout(function() {
                $("#qie10").css("visibility","visible")
                if (10 == startDown1) {
                    hiddenTotal();
                }
            },time)
        }
    }
}

function hiddenTotal() {
    $("#totle").css("visibility","hidden");
    $("#qie1").css("visibility","hidden");
    $("#qie2").css("visibility","hidden");
    $("#qie3").css("visibility","hidden");
    $("#qie4").css("visibility","hidden");
    $("#qie5").css("visibility","hidden");
    $("#qie6").css("visibility","hidden");
    $("#qie7").css("visibility","hidden");
    $("#qie8").css("visibility","hidden");
    $("#qie9").css("visibility","hidden");
    $("#qie10").css("visibility","hidden");
}

function setArray(array) {
    var daWenLuLen=new Array();
    for (var i=0;i<array.length;i++) {
        daWenLuLen[i] = array[i];
    }
    return daWenLuLen;
}

//庄问
function wenluZhuang() {
    hideXian();
    var daWenLuLen = setArray(daLuLen);
    var  wenRow = row;
    var  wenCloun = cloun;
    $("#img" + (juCount + 1) +"").html("<img id='zhuwenlu' class=\"zhupanlu\" src=\"../../image/zhuang.png\" width=\"20px;\">");
    if (up == 0) {
        $("#dalu_" + wenCloun + "" + wenRow +"").html("<div id='daluwen' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
    } else if (up == 1) {
        wenRow = wenRow + 1;
        $("#dalu_" + wenCloun  + "" +wenRow +"").html("<div id='daluwen' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
    } else if (up == 2) {
        daWenLuLen[daWenLuLen.length] = wenRow;
        wenCloun = wenCloun + 1;
        wenRow = 1;
        $("#dalu_" + wenCloun  + "" + wenRow +"").html("<div id='daluwen' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
    }
    var obj=document.getElementById("zhuwenlu");
    var obj2=document.getElementById("daluwen");
    var timer=null;
    var timer2=null;
    var i=0;
    var j=0;
    clearInterval(timer);
    timer=setInterval(function(){
        if (obj != null) {
            obj.style.display=i++%2?"none":"block";
            if (i > 7) {
                clearInterval(timer);
                isClickZhuang = 0;
                obj.parentNode.removeChild(obj);
            }
        }
    },500);
    clearInterval(timer2);
    timer2=setInterval(function(){
        if (obj2 != null) {
            obj2.style.display=j++%2?"none":"block";
            if (j > 7) {
                clearInterval(timer2);
                obj2.parentNode.removeChild(obj2);
            }
        }
    },500);
    setWenLuDaYan(daWenLuLen,wenRow,wenCloun);
    setWenLuXiaoLu(daWenLuLen,wenRow,wenCloun);
    setWenLuZhang(daWenLuLen,wenRow,wenCloun);
}

function hideXian() {
    var obj=document.getElementById("zhuwenluXian");
    var obj2=document.getElementById("daluwenXian");
    if (obj != null) {
        obj.parentNode.removeChild(obj);
    }
    if (obj2 != null) {
        obj2.parentNode.removeChild(obj2);
    }
}

function hideZhang() {
    var obj=document.getElementById("zhuwenlu");
    var obj2=document.getElementById("daluwen");
    if (obj != null) {
        obj.parentNode.removeChild(obj);
    }
    if (obj2 != null) {
        obj2.parentNode.removeChild(obj2);
    }
}

function wenluXian() {
    hideZhang();
    var daWenLuLen = setArray(daLuLen);
    var wenRow = row;
    var wenCloun = cloun;
    //闲问
    $("#img" + (juCount + 1) +"").html("<img id='zhuwenluXian' class=\"zhupanlu\" src=\"../../image/xian.png\" width=\"20px;\">");
    if (up == 0) {
        $("#dalu_" + wenCloun  + "" + wenRow +"").html("<div id='daluwenXian' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
    } else if (up == 1) {
        daWenLuLen[daWenLuLen.length] = wenRow;
        wenRow = 1;
        wenCloun = wenCloun + 1
        $("#dalu_" + wenCloun  + "" + wenRow +"").html("<div id='daluwenXian' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
    } else if (up == 2) {
        wenRow = wenRow + 1
        $("#dalu_" + wenCloun  + "" + wenRow +"").html("<div id='daluwenXian' class=\"dulu\" style=\"width: 14px;height: 14px;border-radius: 14px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
    }
    var obj=document.getElementById("zhuwenluXian");
    var obj2=document.getElementById("daluwenXian");
    var timer=null;
    var timer2=null;
    var i=0;
    var j=0;
    clearInterval(timer);
    timer=setInterval(function(){
        if (obj != null) {
            obj.style.display=i++%2?"none":"block";//还是有收获的。这个写法比if..else想必简单了好多
            if (i > 7) {
                clearInterval(timer);
                isClickZhuang = 0;
                obj.parentNode.removeChild(obj);
            }
        }
    },500);
    clearInterval(timer2);
    timer2=setInterval(function(){
        if (obj2 != null) {
            obj2.style.display=j++%2?"none":"block";//还是有收获的。这个写法比if..else想必简单了好多
            if (j > 7) {
                clearInterval(timer2);
                obj2.parentNode.removeChild(obj2);
            }
        }
    },500);
    setWenLuDaYan(daWenLuLen,wenRow,wenCloun);
    setWenLuXiaoLu(daWenLuLen,wenRow,wenCloun);
    setWenLuZhang(daWenLuLen,wenRow,wenCloun);
}

function setWenLuZhang(daWenLuLen,wenRow,wenCloun) {
    if (wenRow == 1) {
        if (wenCloun > 4) {
            var cloum1 = wenCloun - 1;
            var row1 = daWenLuLen[cloum1-1];
            var cloum2 = wenCloun - 4;
            var row2 = daWenLuLen[cloum2-1];
            if (row1 == row2) {
                if(zhangUp == 0) {
                    $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                } else if (zhangUp == 1) {
                    $("#zhanglanglu_" + zhangCloun + "" + (zhangRow + 1) +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                } else if (zhangUp == 2) {
                    $("#zhanglanglu_" + (zhangCloun + 1) + "1").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                }
            } else {
                if(zhangUp == 0) {
                    $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                } else if (zhangUp == 1) {
                    $("#zhanglanglu_" + (zhangCloun + 1) + "1").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                } else if (zhangUp == 2) {
                    $("#zhanglanglu_" + zhangCloun + "" + (zhangRow + 1) +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                }
            }
        }
    } else {
        //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
        if (wenCloun > 3) {
            var cloum1 = wenCloun - 3;
            var row1 = daWenLuLen[cloum1 - 1];  //上一列的行数
            if (wenRow - row1 == 1) {
                if(zhangUp == 0) {
                    $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                } else if (zhangUp == 1) {
                    $("#zhanglanglu_" + (zhangCloun + 1) + "1").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                } else if (zhangUp == 2) {
                    $("#zhanglanglu_" + zhangCloun + "" + (zhangRow + 1) +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/xianlan.png\" width=\"12px;\">");
                }
            } else {
                //成对，红
                if(zhangUp == 0) {
                    $("#zhanglanglu_" + zhangCloun + "" + zhangRow +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                } else if (zhangUp == 1) {
                    $("#zhanglanglu_" + zhangCloun + "" + (zhangRow + 1) +"").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                } else if (zhangUp == 2) {
                    $("#zhanglanglu_" + (zhangCloun + 1) + "1").html("<img id='wenxiaozhang' class=\"dulu\" src=\"../../image/zhuanghong.png\" width=\"12px;\">");
                }
            }
        }
    }
    var obj=document.getElementById("wenxiaozhang");
    var timer=null;
    var i=0;
    clearInterval(timer);
    timer=setInterval(function(){
        if (obj != null) {
            obj.style.display=i++%2?"none":"block";//还是有收获的。这个写法比if..else想必简单了好多
            if (i > 7) {
                clearInterval(timer);
                obj.parentNode.removeChild(obj);
            }
        }
    },500);
}

function setWenLuXiaoLu(daWenLuLen,wenRow,wenCloun) {
    if (wenRow == 1) {
        if (wenCloun > 3) {
            var cloum1 = wenCloun - 1;
            var row1 = daWenLuLen[cloum1-1];
            var cloum2 = wenCloun - 3;
            var row2 = daWenLuLen[cloum2-1];
            if (row1 == row2) {
                if(xiaoUp == 0) {
                    $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 1) {
                    $("#xiaolu_" + xiaoCloun + "" + (xiaoRow + 1) +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 2) {
                    $("#xiaolu_" + (xiaoCloun + 1) + "1").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else {
                if(xiaoUp == 0) {
                    $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 1) {
                    $("#xiaolu_" + (xiaoCloun + 1) + "1").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 2) {
                    $("#xiaolu_" + xiaoCloun + "" + (xiaoRow + 1) +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                }
            }
        }
    } else {
        //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
        if (wenCloun > 2) {
            var cloum1 = wenCloun - 2;
            var row1 = daWenLuLen[cloum1 - 1];  //上一列的行数
            if (wenRow - row1 == 1) {
                if(xiaoUp == 0) {
                    $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 1) {
                    $("#xiaolu_" + (xiaoCloun + 1) + "1").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 2) {
                    $("#xiaolu_" + xiaoCloun + "" + (xiaoRow + 1) +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else {
                //成对，红
                if(xiaoUp == 0) {
                    $("#xiaolu_" + xiaoCloun + "" + xiaoRow +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 1) {
                    $("#xiaolu_" + xiaoCloun + "" + (xiaoRow + 1) +"").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (xiaoUp == 2) {
                    $("#xiaolu_" + ( xiaoCloun + 1) + "1").html("<div id='wenxiaolu' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;background-color:#ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                }
            }
        }
    }
    var obj=document.getElementById("wenxiaolu");
    var timer=null;
    var i=0;
    clearInterval(timer);
    timer=setInterval(function(){
        if (obj != null) {
            obj.style.display=i++%2?"none":"block";//还是有收获的。这个写法比if..else想必简单了好多
            if (i > 7) {
                clearInterval(timer);
                obj.parentNode.removeChild(obj);
            }
        }
    },500);
}

function setWenLuDaYan(daWenLuLen,wenRow,wenCloun) {
    if (wenRow == 1) {
        if (wenCloun > 2) {
            var cloum1 = wenCloun - 1;
            var row1 = daWenLuLen[cloum1-1];
            var cloum2 = wenCloun - 2;
            var row2 = daWenLuLen[cloum2-1];
            if (row1 == row2) {
                if(daUp == 0) {
                    $("#dayanzai_" + daCloun + "" + daRow +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 1) {
                    $("#dayanzai_" + daCloun + "" + (daRow + 1) +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 2) {
                    $("#dayanzai_" + (daCloun + 1) + "1" ).html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else {
                if(daUp == 0) {
                    $("#dayanzai_" + daCloun + "" + daRow +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 1) {
                    $("#dayanzai_" + (daCloun + 1) + "1").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 2) {
                    $("#dayanzai_" + daCloun + "" + (daRow + 1) +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                }
            }
        }
    } else {
        //2、大路每列的第2粒開始跟前一列比较：有成对是红笔、第一个无对是藍笔、第2个开始无对又是红笔。红笔分为3种情况，上面的第1点，称顶头红笔，上面的第2点，分为有对红笔和下空红笔
        if (wenCloun > 1) {
            var cloum1 = wenCloun - 1;
            var row1 = daWenLuLen[cloum1 - 1];  //上一列的行数
            if (wenRow - row1 == 1) {
                if(daUp == 0) {
                    $("#dayanzai_" + daCloun + "" + daRow +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 1) {
                    $("#dayanzai_" + (daCloun + 1) + "1").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 2) {
                    $("#dayanzai_" + daCloun + "" + (daRow + 1) +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #0c41ff;margin: 0 auto;line-height: 11px;\"></div>");
                }
            } else {
                //成对，红
                if(daUp == 0) {
                    $("#dayanzai_" + daCloun + "" + daRow +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 1) {
                    $("#dayanzai_" + daCloun + "" + ( daRow + 1) +"").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                } else if (daUp == 2) {
                    $("#dayanzai_" + ( daCloun + 1) + "1").html("<div id='wendayan' class=\"dulu\" style=\"width: 10px;height: 10px;border-radius: 10px;border: 2px solid #ff4545;margin: 0 auto;line-height: 11px;\"></div>");
                }
            }
        }
    }
    var obj=document.getElementById("wendayan");
    var timer=null;
    var i=0;
    clearInterval(timer);
    timer=setInterval(function(){
        if (obj != null) {
            obj.style.display=i++%2?"none":"block";//还是有收获的。这个写法比if..else想必简单了好多
            if (i > 7) {
                clearInterval(timer);
                obj.parentNode.removeChild(obj);
            }
        }
    },500);
}