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
            All container List
            <span class="pull-right">

                <button type="button" id="loading-btn" class="btn btn-warning btn-xs" onclick="queryList()">
                    <i class="fa fa-refresh"></i> Refresh
                </button>
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
                <th>ID</th>
                <th>所属服务器</th>
                <th>容器名称</th>
                <th>容器类型</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="containerListContent"></tbody>
        </table>
    </section>
    <!-- page end-->
</section>



<script id="containerListTemplate" type="text/html">
    {{each list item i}}
    <tr>
        <td>{{item.id}}</td>
        <td>【{{item.serverId}}】{{item.serverName}}</td>
        <td>{{item.name}}</td>
        <td>{{item.type}}</td>
        <td>{{item.remark}}</td>
        <td>
            <button type="button" class="btn btn-success btn-sm" onclick="containerStart('{{item.id}}')"><i class="fa fa-play"></i> 启动</button>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-danger btn-sm" onclick="containerStop('{{item.id}}')"><i class="fa fa-stop"></i> 停止</button>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-warning btn-sm" onclick="containerCheck('{{item.id}}')"><i class="fa fa-stethoscope"></i> 检查</button>
        </td>
    </tr>
    {{/each}}
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var SEARCH_PARAMS = ['name', 'remark'];
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
        $("#containerListContent").html(template("containerListTemplate", {list: list}));
    }

    var queryList = function () {
        $.get("/api/containers", function (data) {
            console.log('容器列表', data);
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

    var containerCheck = function(id){
        if(id) {
            Core.post("/api/container/" + id + "/check", {}, function (data) {
                console.log('运行检查脚本：', data);
                if (data.status == 200) {

                }
                alert(data.msg);
            }, true);
        }
    };

    var containerStart = function(id){
        if(id) {
            Core.post("/api/container/" + id + "/start", {}, function (data) {
                console.log('运行检查脚本：', data);
                if (data.status == 200) {

                }
                alert(data.msg);
            }, true);
        }
    };

    var containerStop = function(id){
        if(id) {
            Core.post("/api/container/" + id + "/stop", {}, function (data) {
                console.log('运行检查脚本：', data);
                if (data.status == 200) {

                }
                alert(data.msg);
            }, true);
        }
    };


</script>
</body>
</html>
