var $this;
var $type;
$(function() {

	$('[data-toggle="tooltip"]').tooltip();

	createValid("#themeForm", function() {
		addTheme($type, $this);
	});

	$("#elementType").change(function() {
		alert($(this).val());
	});
	$("input[name=type]").click(function() {
		if ($(this).val() == 0)
			$(".medias").hide();
		else {
			$(".medias").show();
			if ($(this).val() == 1)
				$(".newUrl").attr("onclick", "newAudioFile()");
			else
				$(".newUrl").attr("onclick", "newVideoFile()");
		}

	});

	$(document).on("click", ".deleterow", function() {
		$(this).parent().remove();
	});
	$("input[name=type]:eq(" + $("#type").val() + ")").click();
	$('.time-picker').datetimepicker({
		format : 'yyyy-mm-dd hh:ii:ss',
		autoclose : true,
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN'
	});
});

function submitTheme(type, btn) {
	$this = btn;
	$type = type;
	$("#submitBtn").click();
}

function newVideoFile() {
	var content = "<input class='form-control url pull-left' type='text' name='files'  datatype='*' nullmsg='请填写视频地址'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a>";
	$(".newUrl").parent().before(content);
}

function newAudioFile() {
	var content = "<div class='col-lg-2'><input class='form-control atitle ' type='text' name='titles' placeholder='文件名' datatype='*' nullmsg='请填写文件名' /></div><div class='col-lg-4'><input class='form-control url pull-left' type='text' name='files' placeholder='文件地址'  datatype='*' nullmsg='请填写文件地址'/></div><div class='col-lg-2'><input class='form-control singer pull-left' type='text' name='singers' placeholder='歌手'  datatype='*' nullmsg='请填写歌手'/></div><div class='col-lg-3'><input class='form-control time pull-left' type='text' name='times'  placeholder='播放时间'  datatype='*' nullmsg='请填写播放时长'/></div><div class='col-lg-1'><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a></div>";
	$(".newUrl").parent().before(content);
}

