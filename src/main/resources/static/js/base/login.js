var $btn;
//初始化toastr设置 
toastr.options.escapeHtml = true;
toastr.options.closeButton = true;
toastr.options.progressBar = true;
function login() {
	window.location.href = "login";
}

function logout() {
	$.ajax({
		type : "post",
		url : "logout",
		dataType : 'json',
		error : function(data) {
			response_error(data);
		},
		success : function(data) {
			if (data.result.success) {
				location.href="/";
			} else {
				toastr.info("登出失败");
			}
		}
	});

}

$(function() {
//	$.ajax({
//		type : "get",
//		url : "http://chaxun.1616.net/s.php?type=ip&output=json",
//		dataType : 'jsonp',
//		error : function() {
//			alert("通信错误");
//		},
//		success : function(data) {
//			// alert(data.Isp);
//		}
//	});
	if ($.cookie("rc_chkRememberMe") == "true") {
		$("#chkRememberMe").attr("checked", true);
		$("#loginName").val($.cookie("rc_LoginName"));
		$("#loginPwd").val($.cookie("rc_LoginPwd"));
	}
	

	// 如果记住密码后用户名有更改，则去除记住密码
	$(document).on("keyup change", "#loginName", function() {
		$("#chkRememberMe").removeAttr("checked");
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
function login() {
	var pwd;

	if ($.cookie("rc_chkRememberMe") == "true" && $("#chkRememberMe:checked").length > 0 && $.cookie("rc_LoginName")==$("#loginName").val()){
        pwd = $("#loginPwd").val();
    } else {
        pwd = $.md5($("#loginPwd").val());
    }
	$.ajax({
		type : "post",
		url : "login",
		dataType : 'json',
		data : {
			loginName : $("#loginName").val(),
			password : pwd,
			geetest_challenge : $(".geetest_challenge").val(),
			geetest_validate : $(".geetest_validate").val(),
			geetest_seccode : $(".geetest_seccode").val()
		},
		error : function(data) {
			response_error(data);
			$btn.button('reset');
		},
		success : function(data) {
			if (data.result.success) {
				saveUserInfo(data);
				location.href = data.url;
			}else {
				$btn.button('reset');
				if(captcha){
                    captcha.reset();
				}
				toastr.error(data.result.msg);
			}
		}
	});
}

function saveUserInfo(data) {

    //头像一直更新
    $.cookie("rc_avatar_"+$("#loginName").val(), data.avatar==null?"":data.avatar, {
        expires: 30,
        path: "/"
    });
    if ($("#chkRememberMe").prop("checked")) {
        if (!$.cookie("rc_chkRememberMe") || $("#loginName").val() != $.cookie("rc_LoginName")) {
            console.log(data);
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();
            $.cookie("rc_chkRememberMe", true, {
                expires: 30,
                path: "/"
            }); // 存储一个带7天期限的 cookie
            $.cookie("rc_LoginName", loginName, {
                expires: 30,
                path: "/"
            }); // 存储一个带7天期限的 cookie
            $.cookie("rc_LoginPwd", $.md5(loginPwd), {
                expires: 30,
                path: "/"
            }); // 存储一个带7天期限的 cookie
            //保存token
            $.cookie("rc_access_token", data.access_token, {
                expires: 30,
                path: "/"
            });
            $.cookie("rc_uid", data.uid, {
                expires: 30,
                path: "/"
            });
            $.cookie("rc_avaliable", true, {
                expires: 30,
                path: "/"
            });
        }
    } else {
        $.cookie("rc_chkRememberMe", "false", {
            expires: -1,
            path: "/"
        });
        $.cookie("rc_LoginName", '', {
            expires: -1,
            path: "/"
        });
        $.cookie("rc_LoginPwd", '', {
            expires: -1,
            path: "/"
        });
        $.cookie("rc_avaliable", '', {
            expires: -1,
            path: "/"
        });
    }
}

function keyDown(event) {
	if (event.keyCode == 13) {
		$("#loginsubmit").click();
	}
}

