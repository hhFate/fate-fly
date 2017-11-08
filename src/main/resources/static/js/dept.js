$(function() {
	getFriendList();

	var to = false;
	$(document).on('keyup', '#search', function() {
		if (to) {
			clearTimeout(to);
		}
		to = setTimeout(function() {
			var v = $("#search").val();
			$('.deptList').jstree(true).search(v);
		}, 250);
	});

	$(document).on("click", ".deptList .dept", function() {
		deptDetail($(this)[0].dataset.deptid);
	});
	$(document).on("click", ".deptList .user", function() {
		userDetail($(this)[0].dataset.uid);
	});


	$('.time-picker').datetimepicker({
		format : 'yyyy-mm-dd',
		autoclose : true,
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN',
		minView : 2
	});
	
	$('.time-picker').datetimepicker().on('hide', function(ev) {
		$(this).blur();
	});
	
	$("#userForm").Validform(
			{
				btnReset : "",
				tiptype : function(msg, o, cssctl) {
					switch (o.type) {
					case 1:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_right").removeClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.removeClass("has-success");
						break;
					case 2:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_wrong").addClass(
										"Validform_right").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.addClass("has-success");
						break;
					case 3:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_right").addClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-success")
								.addClass("has-error");
						break;

					default:
						break;
					}

				},
				ignoreHidden : true,
				dragonfly : false,
				tipSweep : false,
				showAllError : true,
				postonce : false,
				datatype : {
					"tel" : /^([0-9]{3,4}-)?[0-9]{7,8}(\-[0-9]{1,4})?$/,
					"date" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
				},
				usePlugin : {
					passwordstrength : {
						minLen : 6,// 设置密码长度最小值，默认为0;
						maxLen : 16,// 设置密码长度最大值，默认为30;
						trigger : function(obj, error) {
							if (error) {
								obj.parent().next().find(".Validform_checktip")
										.show();
								obj.parent().next().find(".passwordStrength")
										.hide();
							} else {
								obj.parent().next().find(".Validform_checktip")
										.hide();
								obj.parent().next().find(".passwordStrength")
										.show();
							}
						}

					}
				},
				beforeCheck : function(curform) {
				},
				beforeSubmit : function(curform) {
				},
				callback : function(data) {
					submitUser();
					return false;
				}
			});

	$("#deptForm").Validform(
			{
				btnReset : "",
				tiptype : function(msg, o, cssctl) {
					switch (o.type) {
					case 1:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_right").removeClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.removeClass("has-success");
						break;
					case 2:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_wrong").addClass(
										"Validform_right").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.addClass("has-success");
						break;
					case 3:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_right").addClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-success")
								.addClass("has-error");
						break;

					default:
						break;
					}

				},
				ignoreHidden : true,
				dragonfly : false,
				tipSweep : false,
				showAllError : true,
				postonce : false,
				datatype : {
					"tel" : /^([0-9]{3,4}-)?[0-9]{7,8}(\-[0-9]{1,4})?$/
				},
				usePlugin : {
					passwordstrength : {
						minLen : 6,// 设置密码长度最小值，默认为0;
						maxLen : 16,// 设置密码长度最大值，默认为30;
						trigger : function(obj, error) {
							if (error) {
								obj.parent().next().find(".Validform_checktip")
										.show();
								obj.parent().next().find(".passwordStrength")
										.hide();
							} else {
								obj.parent().next().find(".Validform_checktip")
										.hide();
								obj.parent().next().find(".passwordStrength")
										.show();
							}
						}

					}
				},
				beforeCheck : function(curform) {
				},
				beforeSubmit : function(curform) {
				},
				callback : function(data) {
					submitDept();
					return false;
				}
			});
});

function submitUser() {
	$("#submit-btn-user").button('loading');
	var options = {
		url : "admin/user/",
		type : 'post',
		dataType : 'json',
		data : {
			password : $.md5($("#pwd").val())
		},
		success : function(data) {
			if (data.result.success) {
				toastr.success("提交成功");
				setTimeout(function() {
					location.reload();
				}, 1500);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn-user").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn-user").button('reset');
		}

	};
	$("#userForm").ajaxSubmit(options);
}

function deleteUser(uid) {
    swal({
            title: "提示",
            text: "确认删除该用户吗？",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "禁用",
            cancelButtonText: "取消",
            closeOnConfirm: false
        }, function () {
			$.ajax({
				type : "delete",
				url : "admin/user/" + uid,
				dataType : 'json',
				data : {},
				success : function(data) {
					if (data.result.success) {
                        swal("Deleted!", "删除成功.", "success");
						setTimeout(function() {
							location.reload();
						}, 700);
					} else {
                        swal("ERROR!", "删除失败", "error");
					}
				}
			});
    });
}

function userDetail(uid) {
	$
			.ajax({
				url : "admin/user/" + uid,
				type : 'get',
				dataType : 'json',
				success : function(data) {
                    var user = data.user;
					$(".show-user .name").val(user.nickname);
					$(".show-user .username").val(user.username);
					$(".show-user input[value=" + user.profile.sex + "]").prop(
							"checked", true);
					$(".show-user .dept").val(user.department.name);
					$(".show-user .mobile").val(user.mobile);
					$(".show-user .tel").val(user.tel);
					$(".show-user .education").val(user.education);
					$(".show-user .proField").val(user.proField);
					$(".show-user .brief").val(user.brief);
					$(".show-user .duty").val(user.duty);
					$(".show-user").show().siblings().hide();
				}
			});
}

function submitDept() {
	$("#submit-btn-dept").button('loading');
	var options = {
		url : "admin/dept/",
		type : 'post',
		dataType : 'json',
		data : {},
		success : function(data) {
			if (data.result.success) {
				toastr.success("提交成功");
				setTimeout(function() {
					location.reload();
				}, 1500);
			} else {
				toastr.error(data.result.msg);
				$("#submit-btn-dept").button('reset');
			}
		},
		error : function(data) {
			response_error(data);
			$("#submit-btn-dept").button('reset');
		}

	};
	$("#deptForm").ajaxSubmit(options);
}

function deleteDept(id) {
    swal({
        title: "提示",
        text: "确认删除该部门吗？",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "禁用",
        cancelButtonText: "取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            type : "delete",
            url : "admin/dept/" + id,
            dataType : 'json',
            data : {},
            success : function(data) {
                if (data.result.success) {
                    swal("Deleted!", "删除成功.", "success");
                    setTimeout(function() {
                        location.reload();
                    }, 700);
                } else {
                    swal("ERROR!", "删除失败", "error");
                }
            }
        });
    });
}

function deptDetail(id) {
	$.ajax({
		url : "admin/dept/" + id,
		type : 'get',
		dataType : 'json',
		success : function(data) {
			$(".show-dept .name").val(data.name);
			$(".show-dept .desc").val(data.desc);
			$(".show-dept .parent").val(data.parent);
			$(".show-dept .leader").val(data.leader.nickname);
			$(".show-dept").show().siblings().hide();
		}
	});
}

