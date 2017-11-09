var $btn;

function logout() {
    $.ajax({
        type: "post",
        url: "logout",
        dataType: 'json',
        error: function (data) {
            response_error(data);
        },
        success: function (data) {
            if (data.result.success) {
                // $.cookie("access_token", "", {
                //     expires: -1
                // });
                // $.cookie("uid", "", {
                //     expires: -1
                // });
                location.href = "/";
            } else {
                dialog.alert("登出失败");
            }
        }
    });

}

function keyDown(event) {
    if (event.keyCode == 13) {
        $("#loginsubmit").click();
    }
}

var dialog = {};
dialog.alert = function (content) {
    swal("提示!", content, "warning");
}

// 初始化toastr设置
toastr.options.escapeHtml = true;
toastr.options.closeButton = true;
toastr.options.progressBar = true;
$(function () {

    $("img.lazy").lazyload({
        effect: "fadeIn"
    });

});

$(function () {

    // 生成二维码
    $('.qr').qrcode({
        width: 25,
        height: 25,
        text: window.location.href
    });
    $('.showQR').qrcode({
        width: 150,
        height: 150,
        text: window.location.href
    });
    $(".qr").hover(function () {
        $(".showQR").show();
    }, function () {
        $(".showQR").hide();
    });

    // var header = new Headroom(document.querySelector("#header"), {
    //     tolerance: 5,
    //     offset: 205,
    //     classes: {
    //         initial: "animated",
    //         pinned: "slideInDown",
    //         unpinned: "slideOutUp"
    //     }
    // });
    //
    // // 响应滚动条的header初始化
    // header.init();



    //到顶的时候隐藏toTop
    $(window).scroll(
        function () {
            if ($(window).scrollTop() > 50) {
                $(".toTopIcon").fadeIn().css("display", "block");
            } else {
                $(".toTopIcon").fadeOut();
            }
        });

});



function toTop() {
    $('html, body').animate({
        scrollTop: 0
    }, '500');
}

function toBottom() {
    var $w = $(window);
    $('html, body').animate({
        scrollTop: $(document).height() - $w.height()
    }, '500');
}

function loadImage(dom, url) {
    var img = new Image();
    img.src = url;
    img.onload = function () {
        // 处理图片加载
        $(dom).attr("src", url);
    }
    img.onerror = function () {
        // 处理图片加载失败
    }
}

// 放在外面，在页面加载完成前执行
$("img[data-url]").each(function () {
    loadImage(this, $(this).data("url"));
});
