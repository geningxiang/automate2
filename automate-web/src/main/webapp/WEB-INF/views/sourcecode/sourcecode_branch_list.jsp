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
                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th>源码仓库ID</th>
                        <th>分支名称</th>
                        <th>最后提交SHA1</th>
                        <th>最后提交者</th>
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
                            <td>${_item.sourceCodeId}</td>
                            <td>${_item.branchName}</td>
                            <td>${_item.lastCommitId}</td>
                            <td>${_item.lastCommitUser}</td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.lastCommitTime}" />
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs" onclick="sourceCodeBranchDetail(${_item.id})"><i class="fa fa-comments"></i> 提交日志 </button>
                                &nbsp;&nbsp;<button class="btn btn-warning btn-xs"><i class="fa fa-refresh"></i> 同步 </button>
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

    function sourceCodeBranchDetail(id) {
        if(id && id > 0)
            window.open("/admin/sourcecode/branchDetail?id=" + id);
    }
</script>
</body>
</html>
