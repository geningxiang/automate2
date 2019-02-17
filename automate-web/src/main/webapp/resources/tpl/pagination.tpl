<div class="pageFooter clearfix">
    <div class="pageInfo">Showing {{(pageNo - 1) * pageSize + 1}} to {{(pageNo - 1) * pageSize + contentSize}} of {{total}} entries</div>
    <ul class="pagination">
        {{if pageNo > 1}}
        <li><a href="javascript:changePage({{pageNo - 1}})">&lt;</a></li>
        <li><a href="javascript:changePage(1)">1</a></li>
        {{else}}
        <li><a href="javascript:void(0)" class="disabled">&lt;</a></li>
        {{/if}}
        {{if pageNo >= 5}}
        <li><a href="javascript:void(0)" class="disabled">···</a></li>
        {{/if}}
        <% for(var i = pageNo - 2; i <= pageNo + 2; i++){ %>
        {{if i == pageNo}}
        <li><a href="javascript:changePage({{i}})" class="active">{{i}}</a></li>
        {{else if i > 1 && i < pageTotal}}
        <li><a href="javascript:changePage({{i}})">{{i}}</a></li>
        {{/if}}
        <% } %>
        {{if pageNo < pageTotal - 5}}
        <li><a class="disabled">···</a></li>
        {{/if}}
        {{if pageNo < pageTotal}}
        <li><a href="javascript:changePage({{pageTotal}})">{{pageTotal}}</a></li>
        <li><a href="javascript:changePage({{pageNo + 1}})">&gt;</a></li>
        {{else}}
        <li><a href="javascript:void(0)" class="disabled">&gt;</a></li>
        {{/if}}
        <li>
            <select class="form-control" onchange="changePageSize(this.value)">
                <option value="20" {{pageSize == 10 ? 'selected': ''}} >10条/页</option>
                <option value="20" {{pageSize == 20 ? 'selected': ''}} >20条/页</option>
                <option value="50" {{pageSize == 50 ? 'selected': ''}} >50条/页</option>
                <option value="100" {{pageSize == 100 ? 'selected': ''}} >100条/页</option>
            </select>
        </li>
    </ul>
</div>