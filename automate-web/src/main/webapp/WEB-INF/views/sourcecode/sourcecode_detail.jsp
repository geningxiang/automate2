<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:useBean id="timestamp" class="java.util.Date"/>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading">项目详情</header>
                <div class="panel-body form-horizontal tasi-form">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">版本控制</label>
                        <div class="col-lg-9">
                            Git
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">仓库地址</label>
                        <label class="col-lg-9 control-label">
                            111
                        </label>
                    </div>
                </div>
            </section>
        </div>
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading">分支列表</header>
                <div id="branchList" class="panel-body">

                </div>
            </section>
        </div>

    </div>
    <!-- page end-->
</section>

<script id="branchListTemplate" type="text/html">

    <table class="table table-hover p-table">
        <tr>
            <th>分支名</th>
            <th>SHA1</th>
            <th>最后提交时间</th>
            <th>最后提交者</th>
        </tr>
        {{each data item i}}
        <tr>
            <td>{{item.branchName}}</td>
            <td>{{item.lastCommitId}}</td>
            <td>{{item.lastCommitTime}}</td>
            <td>{{item.lastCommitUser}}</td>
        </tr>
        {{/each}}
    </table>
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var id = '${id}';
    $(function(){
        init();
    });

    function init() {
        querySourceCodeDetail();
        queryBranchList();
    }

    function querySourceCodeDetail(){
        Core.get('/api/sourcecode/sourceCode/' + id, null, function(msg){
           console.log('查询项目详情', msg);
        });
    }

    function queryBranchList(){
        Core.get('/api/sourcecode/branchList', {id: id}, function(msg){
            console.log('分支列表', msg);
            $("#branchList").html(template('branchListTemplate', msg));
        });
    }
</script>
</body>
</html>
