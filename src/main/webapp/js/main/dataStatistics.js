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

function init() {
    //初始化扑克数组
    for (var i=0;i<416;i++) {
        myArray[i] = i + 1;
    }
    //去掉首切牌10张
    for (var j =0; j<10;j++) {
        random();
    }
}

function random() {
    var index = Math.floor((Math.random() * myArray.length));
    myArray.remove(index);
    return index;
}

/*
* 方法:Array.remove(dx) 通过遍历,重构数组
* 功能:删除数组元素.
* 参数:dx删除元素的下标.
*/
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

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
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#xian1").attr("src","/bjl/image/" + imageName);
    $("#xian1").css("visibility","visible ");
    var xian1 = initData(imageName); //第一张牌点数
    $("#xian").html(showData(xian1));
    $("#xian").css("visibility","visible ");
    setTimeout(function() {faPai2(xian1) },1000);
}

function faPai2(xian1) {
    //庄第一张
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#zhuang1").attr("src","/bjl/image/" + imageName);
    $("#zhuang1").css("visibility","visible ");
    var zhuang1 = initData(imageName); //第一张牌点数
    $("#zhuang").html(showData(zhuang1));
    $("#zhuang").css("visibility","visible ");
    setTimeout(function() {faPai3(xian1,zhuang1) },1000);
}

function faPai3(xian1,zhuang1) {
    //闲第二张
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#xian2").attr("src","/bjl/image/" + imageName);
    $("#xian2").css("visibility","visible ");
    var xian2 = initData(imageName); //第一张牌点数
    $("#xian").html(getCount(showData(xian1) +showData( xian2)));
    $("#xian").css("visibility","visible ");
    setTimeout(function() {faPai4(xian1,zhuang1,xian2) },1000);
}

function faPai4(xian1,zhuang1,xian2) {
    //庄第二张
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#zhuang2").attr("src","/bjl/image/" + imageName);
    $("#zhuang2").css("visibility","visible ");
    var zhuang2 = initData(imageName); //第一张牌点数
    var zhuang = getCount(showData(zhuang1) + showData(zhuang2));
    $("#zhuang").html(zhuang);
    $("#zhuang").css("visibility","visible ");
    //判断是否有对子
    if(xian1 == xian2) {
        xiandui = 0;
    }
    if(zhuang1 == zhuang2) {
        zhuangdui = 0;
    }
    //判断是否需要博牌
    if (getCount(showData(xian1) +showData(xian2)) < 6 && zhuang < 8) {
        //闲需要博牌
        setTimeout(function() {faPaiXian(xian1,zhuang1,xian2,zhuang2) },1000);
    } else {
        if (zhuang < 3 && getCount(showData(xian1) +showData(xian2)) < 8) {
            setTimeout(function() {faPaiZhuang(xian1,zhuang1,xian2,zhuang2,0) },1000);
        } else {
            goGame(xian1,zhuang1,xian2,zhuang2,0,0);
        }
    }
}

function faPaiXian(xian1,zhuang1,xian2,zhuang2) {
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#xian3").attr("src","/bjl/image/" + imageName);
    $("#xian3").css("visibility","visible ");
    var xian3 = initData(imageName); //第一张牌点数
    $("#xian").html(getCount(showData(xian1) + showData(xian2) + showData(xian3)));
    $("#xian").css("visibility","visible ");
    var zhuang =  getCount(showData(zhuang1) + showData(zhuang2));
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
    var index = Math.floor((Math.random() * myArray.length));
    var imageName = "veryhuo.com_pkp_" + myArray[index] + ".jpg";
    myArray.remove(index);
    $("#zhuang3").attr("src","/bjl/image/" + imageName);
    $("#zhuang3").css("visibility","visible ");
    var zhuang3 = initData(imageName);
    $("#zhuang").html(getCount(showData(zhuang1) + showData(zhuang2) + showData(zhuang3)));
    $("#zhuang").css("visibility","visible ");
    //判断是否有对子
    if(zhuang1 == zhuang2 || zhuang1 == zhuang3 || zhuang2 == zhuang3) {
        zhuangdui = 0;
    }
    goGame(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3);
}

function goGame(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3) {
    //提交数据到后台
    if (juCount < 66) {
        juCount = juCount + 1;
        $("#juCount").html(juCount);
        var zhuangdian = getCount(showData(zhuang1) + showData(zhuang2) + showData(zhuang3)); //庄点数
        var xiandian = getCount(showData(xian1) + showData(xian2) + showData(xian3));  //闲点数
        var userMoney =parseFloat($("#userMoney").text());
        var touzhuMoney =parseFloat($("#touzhuMoney").text());
        var clearMoney = parseFloat($("#clearMoney").text());
        var choushui = (touzhuMoney * 0.1);
        $("#clearMoney").html((clearMoney + choushui))
        $("#money").val(0);
        $("#touzhuMoney").text("0.0")
        if (zhuangdian > xiandian) {
            //庄赢
            zhuangCount = zhuangCount + 1;
            $("#zhuangCount").html(zhuangCount);
            $("#msg").html("庄赢，结算中");
            if (radio == 0) {
                $("#userMoney").text(userMoney + (touzhuMoney - choushui))
            } else {
                $("#userMoney").text(userMoney - touzhuMoney );
            }
        } else if (zhuangdian < xiandian) {
            //闲赢
            xianCount = xianCount + 1;
            $("#xianCount").html(xianCount);
            $("#msg").html("闲赢，结算中");
            if (radio == 1) {
                $("#userMoney").text(userMoney + (touzhuMoney - choushui))
            } else {
                $("#userMoney").text(userMoney - touzhuMoney );
            }
        } else {
            //和
            heCount = heCount + 1;
            $("#heCount").html(heCount);
            $("#msg").html("和，结算中");
            if (radio == 2) {
                $("#userMoney").text(userMoney + (touzhuMoney - choushui))
            } else {
                $("#userMoney").text(userMoney - touzhuMoney );
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
        submitDate(xian1,zhuang1,xian2,zhuang2,xian3,zhuang3,userMoney,touzhuMoney,zhuangdian,xiandian);
    } else {
        //新的一大局
        isStart = 0;
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
        myArray.clear();
        init();
        $("#startBut").html("开始游戏");
        $("#endTime").html(time);
        $("#endTime").css("display","block");
        $("#msg").html("上一局结束，请开始下一局游戏");
        $("#msg").css("display","initial");
        $("#touzhuId").removeAttr("disabled");
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
                setTimeout(function() {goOnGame() },5000);
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

/**
 * 投注
 */
function touzhu() {
    var money = parseFloat($("#money").val());
    var userMoney =parseFloat($("#userMoney").text());
    var touzhuMoney =parseFloat($("#touzhuMoney").text());
    if (money <= 0) {
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
    $("#touzhuMoney").html(money + touzhuMoney);
    $("#userMoney").html(userMoney - money);
}

var radio = -1;
function getValue(value){
    radio = value;
}