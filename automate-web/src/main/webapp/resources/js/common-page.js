var PAGE_SIZE_ARRAY = [20, 50, 100];

var toPage = function(formId, pageNo){
    $("#pageNo").val(pageNo);
    $("#" + formId).submit();
};

var changePageSize = function(formId, pageSize){
    $("#pageNo").val(1);
    $("#pageSize").val(pageSize);
    $("#" + formId).submit();

};
$(function () {

    var qryFrmId = $("#qryFrmId").eq(0);


    if (!qryFrmId) {
        return;
    }

    console.log(qryFrmId);

    var pageNo = parseInt(qryFrmId.find("#pageNo").val());
    var pageSize = parseInt(qryFrmId.find("#pageSize").val());
    var totalElements = parseInt(qryFrmId.find("#totalElements").val());
    var totalPage =  parseInt(totalElements / pageSize);
    if(totalElements % pageSize > 0){
        totalPage++;
    }


    var tableEl = $(qryFrmId.attr("data-target"));

    if (!tableEl) {
        return;
    }

    console.log(tableEl);

    var beforeEl = '<div class="row-fluid">';
    beforeEl += '<div class="span6">';
    beforeEl += '<div id="dynamic-table_length" class="dataTables_length">';
    beforeEl += '<label><select class="form-control" onchange="changePageSize(\'qryFrmId\', this.value)">';
    for (var i = 0; i < PAGE_SIZE_ARRAY.length; i++) {
        beforeEl += '<option value="' + PAGE_SIZE_ARRAY[i] + '" ' + (pageSize == PAGE_SIZE_ARRAY[i] ? 'selected' : '') + '>' + PAGE_SIZE_ARRAY[i] + '</option>';
    }
    beforeEl += '</select> records per page</label>';
    beforeEl += '</div></div></div>';

    $(beforeEl).insertBefore(tableEl);



    var afterEl = '<div class="row-fluid">'+
        '   <div class="span6">' +
        '    <div class="dataTables_info" id="dynamic-table_info">' +
        '     Showing '+((pageNo - 1) * pageSize + 1)+' to '+ Math.min((pageNo - 1) * pageSize + pageSize, totalElements)+' of '+totalElements+' entries' +
        '    </div>' +
        '   </div>' +
        '   <div class="span6">' +
        '    <div class="dataTables_paginate paging_bootstrap pagination">' +
        '     <ul>';

    if(pageNo > 1) {
        afterEl += '<li class="first"> <a href="javascript:toPage(\'qryFrmId\',1)"><i class="fa fa-backward"></i>&nbsp;</a></li>';
        afterEl += '<li class="prev"> <a href="javascript:toPage(\'qryFrmId\','+(pageNo-1)+')"><i class="fa fa-caret-left"></i>&nbsp;</a></a></li>';
    }


    for(var i = pageNo - 5; i < pageNo; i++){
        if(i > 0) {
            afterEl += '<li> <a href="javascript:toPage(\'qryFrmId\','+i+')">'+i+'</a></li>';
        }
    }
    afterEl += '<li class="active"><a href="javascript:void(0);">'+pageNo+'</a></li>';

    for(var i = pageNo + 1; i < pageNo + 5 && i <= totalPage; i++){
        if(i > 0) {
            afterEl += '<li> <a href="javascript:toPage(\'qryFrmId\','+i+')">'+i+'</a></li>';
        }
    }

    if(pageNo < totalPage) {
        afterEl += '<li class="next"> <a href="javascript:toPage(\'qryFrmId\','+(pageNo+1)+')"><i class="fa fa-caret-right"></i>&nbsp;</a></li>';
        afterEl += '<li class="last"> <a href="javascript:toPage(\'qryFrmId\','+totalPage+')"><i  class="fa fa-forward"></i>&nbsp;</a></li>';
    }
    afterEl += '</ul>' +
        '    </div>' +
        '   </div>' +
        '  </div>';

    $(afterEl).insertAfter(tableEl);
});