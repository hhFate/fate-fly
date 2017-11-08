$("#selectAll").click(
	function() {
		$(".table tbody input").each(function() {
			if ($("#selectAll").prop("checked") != $(this).prop("checked")){
                $(this).click();
			}
		});
});
$(".table").on("click","input[type=checkbox]",function(){
	$("#selected").val("");
	$(".table tbody input:checked").each(function() {
		$("#selected").val($("#selected").val()+ $(this).val() + ",");
	});
	if ($(".table tbody input:checked").length > 0) {
		$(".pull-left").children().prop("disabled",false);
		$("#reset").prop("disabled",false);
		$("#dels").prop("disabled",false);
		$(".mail-tools-item").prop("disabled",false);
	} else {
		$(".pull-left").children().prop("disabled",true);
		$("#reset").prop("disabled",true);
		$("#dels").prop("disabled",true);
		$(".mail-tools-item").prop("disabled",true);
	}
})
function unSelectAll(){
	$(".table input:checkbox").each(function(){
       $(this).removeAttr("checked");			
	});
	$(".pull-left").children().attr("disabled","disabled");
	$("#selected").val("");
}