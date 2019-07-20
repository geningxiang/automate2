<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
                    分支列表
                </header>
                <table class="table table-hover p-table">
                    <thead>
                    <tr>
                        <th>源码仓库</th>
                        <th>分支名称</th>
                        <th>最后提交者</th>
                        <th>最后提交SHA1</th>
                        <th>最后提交时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(list)==0}">
                        <tr>
                            <td align="center" colspan="7">没有找到相关的记录！</td>
                        </tr>
                    </c:if>
                    <c:forEach var="_item" items="${list}" varStatus="status">
                        <tr>
                            <td>
                                【${_item.projectId}】${projectMap[_item.projectId].name}
                            </td>
                            <td>${_item.branchName}</td>
                            <td>${_item.lastCommitUser}</td>
                            <td>${_item.lastCommitId}</td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.lastCommitTime}" />
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs" onclick="projectBranchDetail(${_item.id})"><i class="fa fa-comments"></i> 提交日志 </button>
                                &nbsp;&nbsp;<button class="btn btn-warning btn-xs" onclick="projectSync(${_item.projectId})"><i class="fa fa-refresh"></i> 同步分支 </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>
    </div>
    <!-- page end-->
</section>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>

    function projectBranchDetail(id) {
        if(id && id > 0)
            window.open("/admin/project/branchDetail?id=" + id);
    }

    function sourceCodeSync(id) {
        Core.post("/api/project/sync", {id: id}, function (data) {
            console.log(data);
            if (data.status == 200) {
                if (data.data > 0) {
                    toastr.success("成功同步" + data.data + "个分支", "提示")
                } else {
                    toastr.info("当前已经是最新的", "提示")
                }
            } else {
                toastr.error(data.msg, "提示")
            }
        })
    }
</script>
</body>
</html>
