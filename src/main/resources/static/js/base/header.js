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
                $.cookie("access_token", "", {
                    expires: -1
                });
                $.cookie("uid", "", {
                    expires: -1
                });
                location.href = "";
            } else {
                var d = dialog({
                    content: "登出失败"
                });
                d.showModal();
                setTimeout(function () {
                    d.close().remove();
                }, 1000);
            }
        }
    });
}

$(function () {
    $("img.lazy").lazyload({
        effect : "fadeIn"
    });

    if ($.cookie("rc_chkRememberMe") == "true") {
        $("#chkRememberMe").attr("checked", true);
        $("#loginName").val($.cookie("rc_LoginName"));
        $("#loginPwd").val($.cookie("rc_LoginPwd"));
    }

    $("#loginsubmit").click(function () {
        $btn = $(this).button('loading');
        login();
    });

    // 如果记住密码后用户名有更改，则去除记住密码
    $(document).on("keyup change", "#loginName", function () {
        $("#chkRememberMe").prop("checked", false);
        $("#loginPwd").val("");
        saveUserInfo();
    });

    $(document).on("keyup change", "#loginPwd", function (e) {
        if (e.keyCode != 13) {
            $("#chkRememberMe").prop("checked", false);
            saveUserInfo();
        }

    });


});



function keyDown(event) {
    if (event.keyCode == 13) {
        $("#loginsubmit").click();
    }
}

function opensearch() {
    if ($("#keyword").val() != "")
        location.href = "theme/search?keyword=" + $("#keyword").val();
}



/**
 * Created by Fate on 2016/6/14.
 */
//初始化toastr设置
toastr.options = {
    "closeButton": false,
    "debug": false,
    "positionClass": "toast-top-right",
    "onclick": null,
    "escapeHtml" : true,
    "progressBar" : true,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

$(function () {
    if ($("#side-menu").length != 0) {
        console.log($("#side-menu").data("left"));
        console.log($("#side-menu>ul>li[data-index=" + $("#side-menu").data("left") + "]").length);
        if ($("#side-menu>ul>li[data-index=" + $("#side-menu").data("left") + "]").length > 0) {
            $("#side-menu>ul>li[data-index=" + $("#side-menu").data("left") + "]").addClass("active").children("ul").show();
        } else {
            $("#side-menu").find("li[data-index=" + $("#side-menu").data("left") + "]").parent().parent().addClass("active").children("ul").addClass("in").attr("aria-expanded", true);
        }
        $("#side-menu").slimScroll({height: "100%", railOpacity: .9, alwaysVisible: !1});
    }
    $(".reinforce-sidebar .reinforce-menu").on("click", "a", function () {
        if ($(this).next().length > 0) {
            $(this).next().slideToggle();
            if ($(this).parent().hasClass("active")) {
                $(this).parent().removeClass("active");
                $(this).children(".pull-right").removeClass("fa-chevron-down").addClass("fa-chevron-right");
            } else {
                $(this).parent().addClass("active").siblings().removeClass("active").children("ul").slideUp();
                $(this).parent().siblings().children("a").children(".pull-right").removeClass("fa-chevron-down").addClass("fa-chevron-right");
                $(this).children(".pull-right").removeClass("fa-chevron-right").addClass("fa-chevron-down");
            }
        }
    });

    $(".reinforce-sidebar .reinforce-menu>li").each(function () {
        if ($(this).children("ul").length > 0) {
            $(this).children("a").append("<i class='fa fa-chevron-right pull-right'></i>");
        }
    });
    $(".navbar-nav a").each(function () {
       if($(this).attr("url")==getUrlRelativePath()||"/"+$(this).attr("url")==getUrlRelativePath()){
           if($(this).parent().parent().hasClass("dropdown-menu")){
               console.log(111);
               $(this).parent().parent().parent().addClass("active").siblings().removeClass("active");
           }else {
               $(this).parent().addClass("active").siblings().removeClass("active");
           }

       }
    });
});

function getUrlRelativePath(){
    var url = document.location.toString();
    var arrUrl = url.split("//");

    var start = arrUrl[1].indexOf("/");
    var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符

    if(relUrl.indexOf("?") != -1){
        relUrl = relUrl.split("?")[0];
    }
    return relUrl;
}
function toTop() {
    $('html, body').animate({
        scrollTop: 0
    }, '500');
}
