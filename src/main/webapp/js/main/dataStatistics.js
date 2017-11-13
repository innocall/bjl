//开始游戏
var time = 30;
var isStart = 0;
var mytime=null;
var isFaPai = false;
var juCount = 0;
var zhuangCount = 0;
var xianCount = 0;
var heCount = 0;
var zhuangDuiCount = 0;
var xianDuiCount = 0;
var myArray=new Array()

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
        juCount = juCount + 1;
        $("#juCount").html(juCount);
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
    $("#xian").html(xian1);
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
    $("#zhuang").html(zhuang1);
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
    $("#xian").html(xian1 + xian2);
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
    $("#zhuang").html(zhuang1 + zhuang2);
    $("#zhuang").css("visibility","visible ");
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