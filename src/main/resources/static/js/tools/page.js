var generatePage = function (page) {
    var content = "";
    content +=  '<nav aria-label="">'
        + '<ul class="pagination pagination-sm">'
        + '<li class="page-item '+(page.pageNum-1==0?"disabled":"")+'">'
        + '<a class="page-link" href="javascript:pageFile('+(page.pageNum-1>0?"1":"")+');" tabindex="-1">&laquo;</a>'
        + '</li>';
    if(page.pageNum-1>0){
        content +=  '<li class="page-item">'
            + '<a class="page-link" href="javascript:pageFile('+(page.pageNum-1<=0?1:page.pageNum-1)+');" tabindex="-1">&lsaquo;</a>'
            + '</li>';
    }
    for(var i=(page.pageNum+page.middle>page.totalPage?(page.totalPage-page.middle*2>0?page.totalPage-page.middle*2:1):(page.pageNum-page.middle>0?page.pageNum-page.middle:1));i<=(page.pageNum+page.middle>page.totalPage?page.totalPage:(page.pageNum-page.middle>0?(page.type%2==0?page.pageNum+page.middle-1:page.pageNum+page.middle):(page.totalPage>page.type?page.type:page.totalPage)));i++){
        content += '<li class="page-item '+(page.pageNum==i?"active":"")+'"><a class="page-link" href=javascript:pageFile('+i+');>'+i+'</a></li>';
    }
    if(page.pageNum<page.totalPage){
        content +=  '<li class="page-item">'
            + '<a class="page-link" href="javascript:pageFile('+(page.pageNum+1>page.totalPage?page.totalPage:page.pageNum+1)+');" tabindex="-1">&rsaquo;</a>'
            + '</li>';
    }
    content += '<li class="page-item '+(page.pageNum==page.totalPage?"disabled":"")+'">'
        + '<a class="page-link" href="javascript:pageFile('+(page.pageNum<page.totalPage?page.totalPage:"")+')">&raquo;</a>'
        + '</li>'
        + '</ul>'
        + '</nav>';
    return content;
}