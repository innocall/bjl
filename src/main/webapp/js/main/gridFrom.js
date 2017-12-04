function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
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
