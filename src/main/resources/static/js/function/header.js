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

function opensearch() {
    if ($("#keyword").val() != "") {
        location.href = "op/search?keyword=" + $("#keyword").val();
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
    var hit = 10; // 显示的下拉提示列表的长度，可修改

    $("#keyword").autocomplete({
        delay: 0,
        source: function (request, response) {
            $.ajax({
                url: "op/getSuggest",
                dataType: 'json',// 如果需要为jsonp类型，则需要在下面的data属性中加上callback:
                // ?
                data: {
                    query: $("#keyword").val()
                },
                success: function (data) {
                    console.log(data);
                    if (data.status === 'OK' && data.result) {
                        if (data.result.length >= hit) {
                            response(data.result.slice(0, hit));
                        } else {
                            response(data.result);
                        }
                    } else if (data.status === 'FAIL' && data.errors) {
                        // alert(data.errors[0].message);
                    }
                }

            });
        }
    });

    if ($(".reinforce-sidebar").length != 0)
        $(".reinforce-menu>li:eq(" + $(".reinforce-sidebar").data("left") + ")")
            .addClass("active").children("ul").show();
    $(".reinforce-sidebar .reinforce-menu")
        .on(
            "click",
            "a",
            function () {
                if ($(this).next().length > 0) {
                    $(this).next().slideToggle(300);
                    if ($(this).parent().hasClass("active")) {
                        $(this).parent().removeClass("active");
                        $(this).children(".pull-right").removeClass(
                            "fa-chevron-down").addClass(
                            "fa-chevron-right");
                    } else {
                        $(this).parent().addClass("active").siblings()
                            .removeClass("active").children("ul")
                            .slideUp(300);
                        $(this).parent().siblings().children("a")
                            .children(".pull-right").removeClass(
                            "fa-chevron-down").addClass(
                            "fa-chevron-right");
                        $(this).children(".pull-right").removeClass(
                            "fa-chevron-right").addClass(
                            "fa-chevron-down");
                    }
                }
            });

    $(".reinforce-sidebar .reinforce-menu>li").each(
        function () {
            if ($(this).children("ul").length > 0) {
                $(this).children("a").append(
                    "<i class='fa fa-chevron-right pull-right'></i>");
            }
        });

    $("img.lazy").lazyload({
        effect: "fadeIn"
    });

    //右侧导航固定
    $("#tags").sticky({topSpacing: 20});
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

    if ($(".table-forum").length > 0) {
        $(".table-forum tr").hover(function () {
            $(this).children().first().children("button").show();
        }, function () {
            $(this).children().first().children("button").hide();
        });
        $(document)
            .on(
                "click",
                ".btn-top",
                function () {
                    var guid = $(this)[0].dataset.guid;
                    swal({
                        title: "置顶",
                        text: "<input class='form-control' id='priority' placeholder='优先级' />",
                        type: "info",
                        html:true,
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes, delete it!",
                        closeOnConfirm: false
                    }, function () {
                        setTop(guid, $("#priority").val());

                    });
                });
        $(document).on("click", ".btn-top-cancel", function () {
            var guid = $(this)[0].dataset.guid;
            setTop(guid, 0);
        });
    }
    // $("#linkForm").Validform(
    //     {
    //         btnReset: "",
    //         tiptype: function (msg, o, cssctl) {
    //             switch (o.type) {
    //                 case 1:
    //                     $(o.obj).parent().parent().removeClass("has-error")
    //                         .removeClass("has-success");
    //                     break;
    //                 case 2:
    //                     $(o.obj).parent().removeClass("has-error").addClass(
    //                         "has-success");
    //                     break;
    //                 case 3:
    //                     $(o.obj).parent().parent().removeClass("has-success")
    //                         .addClass("has-error");
    //                     break;
    //
    //                 default:
    //                     break;
    //             }
    //
    //         },
    //         ignoreHidden: true,
    //         dragonfly: false,
    //         tipSweep: false,
    //         label: ".label",
    //         showAllError: true,
    //         postonce: false,
    //         datatype: {},
    //         usePlugin: {},
    //         beforeCheck: function (curform) {
    //             // 在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
    //             // 这里明确return false的话将不会继续执行验证操作;
    //         },
    //         beforeSubmit: function (curform) {
    //             // 在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
    //             // 这里明确return false的话表单将不会提交;
    //         },
    //         callback: function (data) {
    //             // 返回数据data是json对象，{"info":"demo info","status":"y"}
    //             // info: 输出提示信息;
    //             // status:
    //             // 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
    //             // 你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
    //             // ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**,
    //             // readyState:**, responseText:** }；
    //
    //             // 这里执行回调操作;
    //             // 注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return
    //             // false，则表单不会提交，如果return true或没有return，则会提交表单。
    //             updateFriendLink();
    //             return false;
    //         }
    //     });
    // // 分页相关
    // $(document).on("click", "#jumpBtn", function () {
    //     var page = $(".jumpInput").val();
    //     changePage(page);
    // });

    $(document).on("input", ".jumpInput", function () {
        var reg = /^[1-9][0-9]*$/;
        var _txt = $(this).val();
        if (reg.test(_txt)) {

        } else {
            $(this).val(1);
        }
        if (parseInt(_txt) > parseInt($(this)[0].dataset.sum))
            $(this).val($(this)[0].dataset.sum);
    });

    $(document).on("keydown", ".jumpInput", function (e) {
        if (e.keyCode == 13) {// 按下回车
            var page = $(".jumpInput").val();
            changePage(page);
        }

    });

    $(".panel-title").click(function () {
        if (!$(this).hasClass("panel-static")) {
            $(this).siblings().slideToggle();
            $(this).next().addClass("in");
            $(this).parent().siblings().children(".panel-collapse").slideUp();
        }
    });

    //到顶的时候隐藏toTop
    $(window).scroll(
        function () {
            if ($(window).scrollTop() > 50) {
                $(".toTopIcon").fadeIn().css("display", "block");
            } else {
                $(".toTopIcon").fadeOut();
            }
        });


    // 滚轮下翻页面时，如果导航条到达浏览器窗口顶部，则固定导航条
    // if ($('.panel-right').length > 0) {
    //     var h2 = $('.panel-right:last').offset().top;
    //     $(window).scroll(
    //         function () {
    //             if ($(window).scrollTop() > h2) {
    //                 $('.panel-right:last').css('position', 'fixed').css(
    //                     'top', '27px').css('width', "303px");
    //             } else {
    //                 $('.panel-right:last').removeAttr("style");
    //             }
    //         });
    // }

});

function setTop(guid, priority) {
    $.ajax({
        type: "post",
        url: "admin/theme/setTop",
        dataType: 'json',
        data: {
            guid: guid,
            priority: priority
        },
        success: function (data) {
            if (data.result.success) {
                swal("Success!", data.result.msg, "success");
                setTimeout(function () {
                    location.reload();
                }, 1000);
            } else{
                swal("Error!", data.result.msg, "error");
            }
        }
    });
}

function updateFriendLink() {

    var options = {
        url: "friendLink/add",
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (data) {
            if (data.result.success) {
                dialog.alert("提交成功，请在您的网站上挂上本站链接，站长将会尽快审核~~");
            } else
                dialog.alert(data.result.msg);
        },
        error: function (data) {
            response_error(data);
        }

    };
    $("#linkForm").ajaxSubmit(options);
}

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
