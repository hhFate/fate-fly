$(function(){
	$(document).click(function(e) {
		if (!$(e.target).is(".dropdown")&&!$(e.target).is(".caret"))
			$(".dropdown ul").slideUp();
	});
	if($(document).width()>748){
		$(".search").focus(function(){
			$(this).animate({width:"300px"});
		});
		$(".search").blur(function(){
			$(this).animate({width:"174px"});
		});
	}
	
	$(document).on("input", ".int-only", function(){
		var reg = /^[0-9]*$/;
		var _txt = $(this).val();
		if (reg.test(_txt)) {

		} else {
			$(this).val(0);
		}
	});
});
var dialog = {};
dialog.alert = function (content) {
    swal("提示!", content, "warning");
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
				toastr.info(data.result.msg);
			}
		}
	});
}