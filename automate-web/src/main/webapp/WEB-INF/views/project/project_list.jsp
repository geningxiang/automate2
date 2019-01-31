<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    项目列表
                </header>
                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Descrition</th>
                        <th>Repository</th>
                        <th>Compile</th>
                        <th><i class=" fa fa-edit"></i> Status</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody id="projectListContent">

                    </tbody>
                </table>
            </section>
        </div>
    </div>
    <!-- page end-->
</section>

<script id="projectListTemplate" type="text/html">
    {{each list item i}}
    <tr>
        <td>{{item.name}}</td>
        <td>{{item.remark}}</td>
        <td>
            {{if item.versionType == 0}}
            <i class="fa fa-github"></i>
            {{/if}}
            {{item.versionUrl}}
        </td>
        <td>
            {{if item.compileType == 0}}
                Maven
            {{/if}}
        </td>
        <td>
            {{if item.status == 1}}
            <span class="label label-success label-mini">激活</span>
            {{else}}
            <span class="label label-defalut label-mini">关闭</span>
            {{/if}}

        </td>
        <td>
            <button class="btn btn-success btn-xs"><i class="fa fa-check"></i> 查看详情 </button>
        </td>
    </tr>
    {{/each}}
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var SEARCH_PARAMS = ['name'];
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
        $("#projectListContent").html(template("projectListTemplate", {list: list}));
    }
    var queryList = function () {
        $.get("/api/project/list.do", function (data) {
            console.log('项目列表', data);
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
