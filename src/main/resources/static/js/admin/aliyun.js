//提交基本设置信息
function setSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/aliyun/set",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					window.location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn").button('reset');
		}
	};
	$("#setForm").ajaxSubmit(options);
}
function oSSSetSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/aliyun/oSSSet",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					window.location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn").button('reset');
		}

	};
	$("#ossForm").ajaxSubmit(options);
}

function getBuckets() {
	$("#submit-btn").button('loading');
	$.ajax({
		type : "get",
		url : "admin/aliyun/buckets",
		dataType : 'json',
		data : {
			endpoint : $("#endpoint").val()
		},
		success : function(data) {
			if (data.result.success) {
				var content = "";
				for (var i = 0; i < data.buckets.length; i++) {
					content += "<option value='" + data.buckets[i].name + "'>"
							+ data.buckets[i].name + "</option>";
				}
				$("#bucket").html(content);
			} else {
				toastr.error(data.result.msg);
				setTimeout(function() {
					d.close().remove();
				}, 1000);
			}
			$("#submit-btn").button('reset');
		},
		error : function(data) {
			response_error(data);
		}
	});
}

// 提交防盗链
function refererSubmit() {
	$("#submit-btn2").button('loading');
	var options = {
		url : "admin/aliyun/oSSReferer",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					window.location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn2").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn2").button('reset');
		}

	};
	$("#refererForm").ajaxSubmit(options);
}

// 图片处理
function imgSubmit() {
	$("#submit-btn3").button('loading');
	var options = {
		url : "admin/aliyun/ossImg",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					window.location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn3").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn3").button('reset');
		}

	};
	$("#imgForm").ajaxSubmit(options);
}

function openSearchSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/aliyun/openSearchSet",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					window.location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn").button('reset');
		}

	};
	$("#openSearchForm").ajaxSubmit(options);
}