function createValid(dom, callback) {
    $(dom).Validform(
        {
            btnReset : "",
            tiptype : function(msg, o, cssctl) {
                switch (o.type) {
                    case 1:
                        $(o.obj).parent().removeClass("has-error").removeClass("has-success");
                        break;
                    case 2:
                        $(o.obj).parent().removeClass("has-error").addClass("has-success");
                        break;
                    case 3:
                        $(o.obj).attr("placeholder", msg).parent().removeClass("has-success").addClass("has-error");
                        break;

                    default:
                        break;
                }

            },
            ignoreHidden : true,
            dragonfly : false,
            tipSweep : false,
            label : ".label",
            showAllError : true,
            postonce : false,
            datatype : {
                "*6-20" : /^[^\s]{6,20}$/,
                "z2-16" : /^[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AF\w]{2,16}$/,// 2-16位中文
                "pm" : /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$/,
                "ID" : /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/,// 身份证
                "d" : /^\d+(.\d{1,2})?$/,// 浮点数
                "tel" : /^([0-9]{3,4}-)?[0-9]{7,8}(\-[0-9]{1,4})?$/,// 座机
                "date" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}( \d{1,2})*$/ // 日期
            },
            usePlugin : {},
            beforeCheck : function(curform) {
                // 在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
                // 这里明确return false的话将不会继续执行验证操作;
            },
            beforeSubmit : function(curform) {
                // 在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                // 这里明确return false的话表单将不会提交;
            },
            callback : function(data) {
                // 返回数据data是json对象，{"info":"demo info","status":"y"}
                // info: 输出提示信息;
                // status:
                // 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
                // 你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
                // ajax遇到服务端错误时也会执行回调，这时的data是{ status:**,
                // statusText:**, readyState:**, responseText:** }；

                // 这里执行回调操作;
                // 注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return
                // false，则表单不会提交，如果return true或没有return，则会提交表单。
                callback();
                return false;
            }
        });
}
// 提示以标签形式弹出
function createValidPopover(dom, callback) {
    $(dom).Validform(
        {
            btnReset : "",
            tiptype : function(msg, o, cssctl) {
                switch (o.type) {
                    case 1:
                        $(o.obj).tooltip('destroy');
                        $(o.obj).parent().removeClass("has-error").removeClass("has-success");
                        break;
                    case 2:
                        $(o.obj).tooltip('destroy');
                        $(o.obj).parent().removeClass("has-error").addClass("has-success");
                        break;
                    case 3:
                        $(o.obj).tooltip('destroy');
                        $(o.obj).data("placement", "top");
                        $(o.obj).data("toggle", "tooltip");
                        $(o.obj).data("title", msg);
                        $(o.obj).tooltip('show');
                        $(o.obj).parent().removeClass("has-success").addClass("has-error");
                        break;

                    default:
                        break;
                }
            },
            ignoreHidden : true,
            dragonfly : false,
            tipSweep : false,
            label : ".label",
            showAllError : true,
            postonce : false,
            datatype : {
                "*6-20" : /^[^\s]{6,20}$/,
                "z2-16" : /^[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AF\w]{2,16}$/,// 2-16位中文
                "pm" : /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$/,
                "ID" : /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/,// 身份证
                "d" : /^\d+(.\d{1,2})?$/,// 浮点数
                "tel" : /^([0-9]{3,4}-)?[0-9]{7,8}(\-[0-9]{1,4})?$/,// 座机
                "date" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}( \d{1,2})*$/ // 日期
            },
            usePlugin : {},
            beforeCheck : function(curform) {
            },
            beforeSubmit : function(curform) {
            },
            callback : function(data) {
                callback();
                return false;
            }
        });
}
