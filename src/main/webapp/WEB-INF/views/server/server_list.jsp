<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <section class="panel">
        <header class="panel-heading">
            All server List
            <span class="pull-right">

                <button type="button" id="loading-btn" class="btn btn-warning btn-xs" onclick="queryList()">
                    <i class="fa fa-refresh"></i> Refres
                </button>

                <a href="#" class=" btn btn-success btn-xs"> Create New Server</a>
            </span>
        </header>
        <div class="panel-body">
            <div class="row">

                <div class="col-md-12">
                    <div class="input-group">
                        <input type="text" id="searchKey" placeholder="Search Here" class="input-sm form-control">
                        <span class="input-group-btn">
                              <button type="button" class="btn btn-sm btn-success"
                                      onclick="showList()"> Go!</button> </span></div>
                </div>
            </div>
        </div>
        <table class="table table-hover p-table">
            <thead>
            <tr>
                <th>名称</th>
                <th>IP</th>
                <th>Project Progress</th>
                <th>状态</th>
                <th>Custom</th>
            </tr>
            </thead>
            <tbody id="serverListContent"></tbody>
        </table>
    </section>
    <!-- page end-->
</section>

<script id="serverListTemplate" type="text/html">
    {{each list item i}}
    <tr>
        <td class="p-name">
            <i class="fa fa-linux"></i> {{item.name}}
        </td>
        <td class="p-team">
            <small>外网：{{item.outsideIp}}</small>
            <br>
            <small>内网：{{item.insideIp}}</small>
        </td>
        <td class="p-progress">
            <div class="progress progress-xs">
                <div style="width: 65%;" class="progress-bar progress-bar-success"></div>
            </div>
            <small>65% Complete</small>
        </td>
        <td>
            {{if item.status == 1}}
            <span class="label label-primary">Active</span>
            {{else}}
            <span class="label label-default">Inactive</span>
            {{/if}}
        </td>
        <td>
            <a href="project_details.html" class="btn btn-primary btn-xs"><i class="fa fa-folder"></i> View </a>
            <a href="#" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>
            <!--
            <a href="#" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Delete </a>
            -->
        </td>
    </tr>
    {{/each}}
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var SEARCH_PARAMS = ['insideIp', 'name', 'outsideIp', 'remark'];
    var dataCache = [];
    var showList = function () {
        var list = [];
        var searchKey = $("#searchKey").val();
        if (searchKey) {
            for (var i = 0; i < dataCache.length; i++) {
                for (var j = 0; j < SEARCH_PARAMS.length; j++) {
                    if (dataCache[i][SEARCH_PARAMS[j]] && dataCache[i][SEARCH_PARAMS[j]].indexOf(searchKey) > -1) {
                        list.push(dataCache[i]);
                        break;
                    }
                }
            }
        } else {
            list = dataCache;
        }
        $("#serverListContent").html(template("serverListTemplate", {list: list}));
    }
    var queryList = function () {
        $.get("/api/server/list.do", function (data) {
            console.log('服务器列表', data);
            if (data.status == 200) {
                dataCache = data.data;
                showList();
            }
        });
    };
    $(function () {
        queryList();


        $("#searchKey").on("keydown", function (e) {
            if (e && e.keyCode == 13) { // enter 键
                showList();
            }
        });

    });

</script>
</body>
</html>
