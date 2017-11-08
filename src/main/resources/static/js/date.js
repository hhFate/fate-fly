Date.prototype.format = function (format) {
    var days = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds(), //millisecond
        "E": days[this.getDay()]
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 * 将日期格式化成xx时间前，今天，等等
 * 类似微博的时间格式
 * @param date
 */
var recentFormat = function (timestamp) {
    var now = Date.parse(new Date());
    var mi = now - timestamp;
    var current = new Date();
    var time = new Date(timestamp);
    if (mi < 60000) {
        return parseInt(mi/1000)+"秒前";
    }else if(mi < 3600000){
        return parseInt(mi/60000)+"分钟前";
    } else if(current.format("yyyy-MM-dd")==time.format("yyyy-MM-dd")){
        return "今天 "+time.format("hh:mm");
    } else if(current.format("yyyy-MM")==time.format("yyyy-MM")&&current.getDate()-time.getDate()==1) {
        return "昨天 "+time.format("hh:mm");
    }else{
        return time.format("yyyy年MM月dd日 hh:mm");
    }
}