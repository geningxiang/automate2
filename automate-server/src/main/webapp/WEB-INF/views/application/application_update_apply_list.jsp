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
           应用更新申请列表
        </header>
        <div class="panel-body" id="applicationUpdateApplyList">
        </div>
    </section>
    <!-- page end-->
</section>

<script id="applicationUpdateApplyListTemplate" type="text/html">
    <table class="table table-hover p-table">
        <tr>
            <th>ID</th>
            <th>项目</th>
            <th>更新包</th>
            <th>应用</th>
            <th>申请用户</th>
            <th>申请时间</th>
            <th>状态</th>
            <th>审核用户</th>
            <th>审核结果</th>
            <th>操作</th>
        </tr>
        {{each content item i}}
        <tr>
            <td>{{item.id}}</td>
            <td>【{{item.projectId}}】</td>
            <td>【{{item.projectPackageId}}】</td>
            <td>【{{item.applicationId}}】</td>
            <td>【{{item.createUserId}}】</td>
            <td>{{dateFormat(item.createTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{item.status}}</td>
            <td>{{item.auditUserId || '-'}}</td>
            <td></td>
            <td>
                {{ if item.status == 0 }}
                <button class="btn btn-success btn-xs" onclick="updateConfirm('{{item.id}}')">
                    <i class="fa fa-hand-o-right"></i> 确认更新
                </button>
                {{/if}}
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
    var pageSize = 10;


    function queryList() {
        $.get("/api/applicationUpdate/applys", {pageNo: pageNo, pageSize: pageSize} , function (msg) {
            console.log('应用更新申请列表', msg);
            if (msg.status == 200) {
                $("#applicationUpdateApplyList").html(template("applicationUpdateApplyListTemplate", msg.data));
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

    function updateConfirm(applyId){
        $.post('/api/applicationUpdate/confirm', {applyId : applyId}, function(msg){
            console.log('确认更新', msg);
            if (msg.status == 200) {

            }
            alert(msg.msg);
        });

    }

</script>
</body>
</html>
