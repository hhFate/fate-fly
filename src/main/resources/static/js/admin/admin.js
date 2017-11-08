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
        if ($("#side-menu>li[data-index=" + $("#side-menu").data("left") + "]").length > 0) {
            $("#side-menu>li[data-index=" + $("#side-menu").data("left") + "]").addClass("active").children("ul").show();
        } else {
            $("#side-menu").find("li[data-index=" + $("#side-menu").data("left") + "]").parent().parent().addClass("active").children("ul").addClass("in").attr("aria-expanded", true);
        }
        // $(".side-menu").slimScroll({height: "100%", railOpacity: .9, alwaysVisible: !1});
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
});
