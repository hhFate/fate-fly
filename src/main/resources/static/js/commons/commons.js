var constants = {};
constants.LOGIN_ERROR_1 = "用户名不存在";
constants.LOGIN_ERROR_2 = "验证码错误";
constants.LOGIN_ERROR_3 = "密码错误";

var response_error = function(data){
	if (data.status == 404)
		toastr.error("请求地址不存在");
	else if (data.status == 500)
		toastr.error("系统内部错误");
	else if (data.status == 200) {
		toastr.error("登录超时，为保证您填写的内容不丢失，请勿刷新页面，并在新开页面中重新登录");
	} else{
        toastr.error("通信异常");
	}
}