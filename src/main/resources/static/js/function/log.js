$(function() {
	// 初始化富文本编辑器
	KindEditor.ready(function(K) {
		var editor = K.create("#contentEditer", {
			uploadJson : '/file/uploadImg',
			allowFileManager : false,
			htmlTags : {
				font : [ 'color', 'size', 'face', '.background-color' ],
				span : [ '.color', '.background-color', '.font-size',
						'.font-family', '.background', '.font-weight',
						'.font-style', '.text-decoration', '.vertical-align',
						'.line-height' ],
				div : [ 'align', '.border', '.margin', '.padding',
						'.text-align', '.color', '.background-color',
						'.font-size', '.font-family', '.font-weight',
						'.background', '.font-style', '.text-decoration',
						'.vertical-align', '.margin-left', 'id', 'class',
						'data-date', 'data-date-format', 'style' ],
				table : [ 'border', 'cellspacing', 'cellpadding', 'width',
						'height', 'align', 'bordercolor', '.padding',
						'.margin', '.border', 'bgcolor', '.text-align',
						'.color', '.background-color', '.font-size',
						'.font-family', '.font-weight', '.font-style',
						'.text-decoration', '.background', '.width', '.height',
						'.border-collapse' ],
				'td,th' : [ 'align', 'valign', 'width', 'height', 'colspan',
						'rowspan', 'bgcolor', '.text-align', '.color',
						'.background-color', '.font-size', '.font-family',
						'.font-weight', '.font-style', '.text-decoration',
						'.vertical-align', '.background', '.border' ],
				a : [ 'href', 'target', 'name', 'id', 'data-*', 'ref' ],
				embed : [ 'src', 'width', 'height', 'type', 'loop',
						'autostart', 'quality', '.width', '.height', 'align',
						'allowscriptaccess' ],
				img : [ 'src', 'width', 'height', 'border', 'alt', 'title',
						'align', '.width', '.height', '.border' ],
				'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [ 'align',
						'.text-align', '.color', '.background-color',
						'.font-size', '.font-family', '.background',
						'.font-weight', '.font-style', '.text-decoration',
						'.vertical-align', '.text-indent', '.margin-left' ],
				'pre,span' : [ 'class' ],
				i : [ 'class' ],
				property : [ 'name', 'value', 'ref' ],
				bean : [ 'id', 'class', 'p:connection-factory-ref', '/' ],
				hr : [ 'class', '.page-break-after' ],
				'script,iframe' : [ 'src', 'style' ],
				'br,tbody,tr,strong,b,sub,sup,em,u,strike,s,del,code' : [],
				'dt,dd' : [ 'style' ],
				'audio,video' : [ 'src', 'preload', 'loop', 'controls',
						'autoplay', 'height', 'width' ],
				'button,input' : [ 'class', 'id', 'size', 'type', 'value',
						'placeholder', 'onclick' ],
				link : [ 'rel', 'href' ]
			},
			resizeType : 1,
			urlType : "domain",
			afterBlur : function(e) {
				$('#contentEditer').val(editor.html());
				$('#contentEditer').blur();
			},
			afterChange : function() {
				$('.word_count1').html(this.count()); // 字数统计包含HTML代码
				var limitNum = 1000; // 设定限制字数
				var pattern = '还可以输入' + limitNum + '字';
				$('.word_surplus').html(pattern); // 输入显示
				if (this.count('text') > limitNum) {
					pattern = ('字数超过限制，请适当删除部分内容');
					// 超过字数限制自动截取
					var strValue = editor.text();
					strValue = strValue.substring(0, limitNum);
					editor.text(strValue);
				} else {
					// 计算剩余字数
					var result = limitNum - this.count();
					pattern = '还可以输入' + result + '字';
				}
				$('.word_surplus').html(pattern); // 输入显示
				// //////
			}

		});
		html = editor.html();

		// 同步数据后可以直接取得textarea的value
		editor.sync();
		html = $('#contentEditer').val(); // jQuery

		// 设置HTML内容
		// editor.html('HTML内容');
	});

	// 初始化表单验证
	$("#logForm").Validform(
			{
				btnReset : "",
				tiptype : function(msg, o, cssctl) {
					switch (o.type) {
					case 1:
						$(o.obj).parent().children(":eq(0)").children(
								".Validform_checktip").removeClass(
								"Validform_right").removeClass(
								"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.removeClass("has-success");
						break;
					case 2:
						$(o.obj).parent().children(":eq(0)").children(
								".Validform_checktip").removeClass(
								"Validform_wrong").addClass("Validform_right")
								.html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.addClass("has-success");
						break;
					case 3:
						$(o.obj).parent().children(":eq(0)").children(
								".Validform_checktip").removeClass(
								"Validform_right").addClass("Validform_wrong")
								.html(msg);
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
					"d" : /^\d+(.\d{1,2})?$/,
					"date" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}( \d{1,2})*$/
				},
				usePlugin : {
					passwordstrength : {
						minLen : 6,// 设置密码长度最小值，默认为0;
						maxLen : 16,// 设置密码长度最大值，默认为30;
						trigger : function(obj, error) {
							if (error) {
								obj.parent().parent().next().find(
										".Validform_checktip").show();
								obj.parent().parent().next().find(
										".passwordStrength").hide();
							} else {
								obj.parent().parent().next().find(
										".Validform_checktip").hide();
								obj.parent().parent().next().find(
										".passwordStrength").show();
							}
						}

					}
				},
				beforeCheck : function(curform) {
				},
				beforeSubmit : function(curform) {
				},
				callback : function(data) {
					submitLog();
					return false;
				}
			});
	
});

function submitLog() {
	$("#submit-btn").button('loading');
	var type=$("#type").val();
	var options = {
		url : "log/"+(type==0?"":$("#id").val()),
		type : type==0?'post':'put',
		dataType : 'json',
		data : {
		},
		success : function(data) {
			if (data.result.success) {
				toastr.success("提交成功");
				setTimeout(function() {
					location.href = "log/index?date="+$("#date").val();
				}, 1500);
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
	$("#logForm").ajaxSubmit(options);
}

function del(id) {
	var d = dialog({
		title : '提示',
		content : "确定删除吗？",
		width : '200px',
		okValue : '确定',
		ok : function() {
			$.ajax({
				type : "delete",
				url : "log/" + id,
				dataType : 'json',
				data : {},
				success : function(data) {
					if (data.result.success) {
						toastr.success("删除成功!");
						location.href="log/index";
					} else {
						toastr.error(data.result.msg);
					}
				}
			});
		},
		cancelValue : "取消",
		cancel : function() {
		}
	});
	d.showModal();
}