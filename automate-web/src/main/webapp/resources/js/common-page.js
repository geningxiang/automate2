var PAGE_SIZE_ARRAY = [20, 50, 100];
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
    beforeEl += '<label><select class="form-control">';
    for (var i = 0; i < PAGE_SIZE_ARRAY.length; i++) {
        beforeEl += '<option value="' + PAGE_SIZE_ARRAY[i] + '" ' + (pageSize == PAGE_SIZE_ARRAY[i] ? 'selected' : '') + '>' + PAGE_SIZE_ARRAY[i] + '</option>';
    }
    beforeEl += '</select> records per page</label>';
    beforeEl += '</div></div></div>';

    $(beforeEl).insertBefore(tableEl);



    var afterEl = '<div class="row-fluid">'+
        '   <div class="span6">' +
        '    <div class="dataTables_info" id="dynamic-table_info">' +
        '     Showing '+((pageNo - 1) * pageSize + 1)+' to '+((pageNo - 1) * pageSize + pageSize)+' of '+totalElements+' entries' +
        '    </div>' +
        '   </div>' +
        '   <div class="span6">' +
        '    <div class="dataTables_paginate paging_bootstrap pagination">' +
        '     <ul>';

    afterEl += '<li><a href="javascript:toPage(\'qryFrmId\',1)"><i class="fa fa-backward"></i>&nbsp;</a></li>';
    afterEl += '<li><a href="javascript:toPage(\'qryFrmId\',1)"><i class="fa fa-caret-left"></i>&nbsp;</a></li>';



    afterEl += '<li class="prev disabled"><a href="#"><i class="fa fa-backward"></i>← Previous</a></li>' +
        '      <li class="active"><a href="#">1</a></li>' +
        '      <li><a href="#">2</a></li>' +
        '      <li><a href="#">3</a></li>' +
        '      <li><a href="#">4</a></li>' +
        '      <li><a href="#">5</a></li>' +
        '      <li class="next"><a href="#">Next → </a></li>' +
        '     </ul>' +
        '    </div>' +
        '   </div>' +
        '  </div>';

    $(afterEl).insertAfter(tableEl);
});