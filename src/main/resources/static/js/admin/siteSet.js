$(function () {
	$('[data-toggle="tooltip"]').tooltip();
})
//站点基本信息更新
function infoSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/siteInfo",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#infoForm").ajaxSubmit(options);
}

// 极验验证的配置更新
function geetestSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/geetest",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#geetestForm").ajaxSubmit(options);
}

function ssoSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/sso",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#ssoForm").ajaxSubmit(options);
}

function rongSubmit() {
    $("#submit-btn").button('loading');
    var options = {
        url : "admin/siteSet/rong",
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if (data.result.success) {
                toastr.success(data.result.msg);
                setTimeout(function() {
                    location.reload();
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
    $("#rongForm").ajaxSubmit(options);
}

// 邮件SMTP配置更新
function mailSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/mail",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#mailForm").ajaxSubmit(options);
}

// 发送测试邮件
function sendTestEmail() {
	$.ajax({
		type : "post",
		url : "admin/siteSet/sendTestEmail",
		dataType : 'json',
		data : {},
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.result.msg);
			}
		},
		error : function(data) {
			response_error(data);
		}
	});
}


//qq登录配置更新
function qqSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/qq",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#qqForm").ajaxSubmit(options);
}


//短信服务配置更新
function smsSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/sms",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#smsForm").ajaxSubmit(options);
}


//新浪微博登录配置更新
function xinlangSubmit() {
	$("#submit-btn").button('loading');
	var options = {
		url : "admin/siteSet/xinlang",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result.success) {
				toastr.success(data.result.msg);
				setTimeout(function() {
					location.reload();
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
	$("#xinlangForm").ajaxSubmit(options);
}

/* 导航设置相关配置开始 */
$(function() {
    $.ajax({//获取导航树
        type : "get",
        url : "admin/siteSet/getNaviRoot",
        dataType : 'json',
        success : function(data) {
            $(".ibox-content").find(".table").children("tbody").html(
                decodeNavi(data));
            $(".l-wrapper").hide();
        }
    });
});

//把导航树从json解析橙html
function decodeNavi(data) {
    var content = "";
    for (var i = 0; i < data.length; i++) {
        content += "<tr class='forum'>"
            + "<td><input type='text' class='form-control order_of_show' name='order' value='"
            + data[i].order
            + "' /><input type='hidden' name='type' value='" + data[i].type
            + "' /></td>" + "<td";
        if (data[i].type == 2)
            content += " class='td-forum'";
        content += "><input class='form-control navi_name pull-left' type='text' name='name' value='"
            + data[i].name
            + "' /><input type='hidden' name='id' value='"
            + data[i].id + "' />";

        content += "</td>"
            + "<td><input class='form-control navi_url pull-left' type='text' name='url' value='"
            + data[i].url + "' /></td>" + "<td align='ibox-content'>"
            + "<div class='btn-group btn-group-xs'>" + "</div>&nbsp;&nbsp;"
            + "<div class='btn-group btn-group-xs'>"
            + "<button class='btn btn-danger del' onclick=delNavi('"
            + data[i].id + "')>删除</button>" + "</div>" + "</td>" + "</tr>"
            + decodeNavi(data[i].children);
        if (data[i].type == 1) {
            content += "<tr>" + "<td></td>" + "<td colspan='3'>"
                + "<a href='javascript:;' class='add newSubNavi' id='"
                + data[i].id + "' rel='nofollow'>"
                + "<span class='glyphicon glyphicon-plus'></span>"
                + "&nbsp;添加新下拉菜单项" + "</a>" + "</td>" + "</tr>";
        }
    }
    return content;
}

//新建导航项
function newNavi() {
    var content = "<tr class='forum'>"
        + "<td><input class='form-control order_of_show' type='text' name='order' value='0' /><input type='hidden' name='type' value='1' /></td>"
        + "<td><input class='form-control navi_name pull-left' type='text' name='name' /><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='id' value='0' /></td>"
        + "<td><input class='form-control navi_url pull-left' type='text' name='url' /></td>"
        + "<td align='ibox-content'>" + "</td>" + "</tr>";
    $(".ibox-content").find(".table").children("tbody").append(content);
}

//新建二级导航
$(document).on("click", ".newSubNavi", function() {
    var content = "<tr class='forum'>"
        + "<td><input class='form-control order_of_show' type='text' name='order' value='0' /><input type='hidden' name='type' value='2' /></td>"
        + "<td class='td-forum'><input class='form-control navi_name pull-left' type='text' name='name'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='id' value='0' /><input type='hidden' name='parentId' value='"
        + $(this).attr("id")
        + "' /></td>"
        + "<td><input class='form-control navi_url pull-left' type='text' name='url' /></td>"
        + "<td align='ibox-content'>" + "</td>"
        + "</tr>";
    $(this).parent().parent().before(content);
});

//删除导航
$(document).on("click", ".deleterow", function() {
    $(this).parent().parent().remove();
});

function naviSubmit() {
    $("#submit-btn").button('loading');
    var result = "";
    $(".ibox-content")
        .find(".table")
        .children("tbody")
        .children(".forum")
        .each(
            function() {
                result += "{type:'"
                    + $(this).find("input[name=type]").val()
                    + "',order:'"
                    + $(this).find("input[name=order]").val()
                    + "',name:'"
                    + $(this).find("input[name=name]").val()
                    + "',url:'"
                    + $(this).find("input[name=url]").val()
                    + "',id:'"
                    + $(this).find("input[name=id]").val()
                    + "',parentId:"
                    + ($(this).find("input[name=parentId]").length == 0 ? 0
                        : $(this).find("input[name=parentId]")
                            .val()) + "},";

            });
    if (result.length > 0) {
        result = result.substring(0, result.length - 1);
        result = "[" + result + "]";
        $.ajax({
            type : "post",
            url : "admin/siteSet/navi",
            dataType : 'json',
            data : {
                result : result
            },
            error : function(data) {
                response_error(data);
                $("#submit-btn").button('reset');
            },
            success : function(data) {
                if (data.result.success){
                    toastr.success("更新成功");
                    setTimeout(function(){location.reload()},1500);
                }
                else {
                    toastr.error(data.message);
                    $("#submit-btn").button('reset');
                }

            }
        });
    }
}

//删除已存在的导航
function delNavi(id) {
    var d = dialog({
        content : "确认删除该消息？",
        title : '提示',
        width : '200px',
        okValue : '确定',
        ok : function() {
            $.ajax({
                type : "delete",
                url : "admin/siteSet/navi/"+id,
                dataType : 'json',
                data : {
                },
                error : function(data) {
                    response_error(data);
                },
                success : function(data) {
                    if (data.result.success)
                        location.reload();
                    else {
                        dialog.alert(data.message);
                    }

                }
            });
            return true;
        },
        cancelValue : "取消",
        cancel : function() {
        }
    });
    d.showModal();
}
/* 导航设置相关配置结束 */

