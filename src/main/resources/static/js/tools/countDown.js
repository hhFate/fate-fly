function countDown(dom) {
    var $send = $(dom);
    var i = 120;
    $send.addClass("gray").css({
        cursor: "default"
    }).prop("disabled", true);
    var interval = setInterval(function () {
        $send.html("剩余" + i + "秒");
        i--;
        if (i < 0) {
            $send.removeClass("gray").css({
                cursor: "pointer"
            }).removeAttr("disabled");
            $send.html("重新获取");
            clearInterval(interval);
        }
    }, 1000);
}