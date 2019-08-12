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
            应用更新日志列表
        </header>
        <div class="panel-body" id="applicationUpdateLogList">
        </div>
    </section>
    <!-- page end-->
</section>

<script id="applicationUpdateLogListTemplate" type="text/html">
    <table class="table table-hover p-table">
        <tr>
            <th>ID</th>
            <th>项目</th>
            <th>服务器</th>
            <th>应用</th>
            <th>更新申请ID</th>
            <th>开始时间</th>
            <th>完成时间</th>
            <th>状态</th>
            <th>更新前后sha256</th>
            <th>操作</th>
        </tr>
        {{each content item i}}
        <tr>
            <td>{{item.id}}</td>
            <td>【{{item.projectId}}】</td>
            <td>【{{item.serverId}}】</td>
            <td>【{{item.applicationId}}】</td>
            <td>【{{item.applyId}}】</td>
            <td>{{dateFormat(item.createTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{dateFormat(item.doneTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{item.status}}</td>
            <td>
                <p>更新前: {{item.beforeSha256}}</p>
                <p>更新后: {{item.afterSha256}}</p>
            </td>
            <td>
                <button class="btn btn-success btn-xs" onclick="compare('{{item.beforeSha256}}','{{item.afterSha256}}')">
                    <i class="fa fa-hand-o-right"></i> 文件树比对
                </button>
            </td>
        </tr>
        {{/each}}
    </table>
    {{@pageFooter($data)}}
</script>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var cacheMap = {};
    var pageNo = 1;
    var pageSize = 20;


    function queryList() {
        $.get("/api/applicationUpdate/logs", {pageNo: pageNo, pageSize: pageSize} , function (msg) {
            console.log('应用更新日志列表', msg);
            if (msg.status == 200) {
                $("#applicationUpdateLogList").html(template("applicationUpdateLogListTemplate", msg.data));
            }
        });
    }

    function changePage(n) {
        pageNo = n;
        queryList();
    }

    function changePageSize(val) {
        if (val) {
            pageNo = 1;
            pageSize = val;
            queryList();
        }
    }

    $(function () {
        queryList();
    });


</script>
</body>
</html>
