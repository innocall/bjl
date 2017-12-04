function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}

/*
* 方法:Array.remove(dx) 通过遍历,重构数组
* 功能:删除数组元素.
* 参数:dx删除元素的下标.
*/
function removArrayByIndex(array,dx) {
    if(isNaN(dx)||dx>array.length){return false;}
    var myArray=new Array()
    for(var i=0,n=0;i<array.length;i++){
        if(i!=dx){
            myArray[n++]=array[i]
        }
    }
    return myArray;
}

Array.prototype.remove=function(dx) {
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++){
        if(i!=dx){
            this[n++]=this[i]
        }
    }
    this.length-=1
}

//功能：将浮点数四舍五入，取小数点后2位
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x*100)/100;
    return f;
}

function getValue(){
    // method 1
    var str = -1;
    var radio = document.getElementsByName("radio");
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            str = radio[i].value;
        }
    }
    return str;
}
