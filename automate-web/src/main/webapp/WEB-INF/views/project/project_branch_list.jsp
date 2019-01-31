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
                        <th>项目</th>
                        <th>分支名称</th>
                        <th>最后提交ID</th>
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
                            <td>${_item.projectId}</td>
                            <td>${_item.branchName}</td>
                            <td>${_item.lastCommitId}</td>
                            <td>${_item.lastCommitUser}</td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.lastCommitTime}" />
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs"><i class="fa fa-comments"></i> 提交日志 </button>
                                &nbsp;&nbsp;<button class="btn btn-warning btn-xs"><i class="fa fa-refresh"></i> 强制同步 </button>
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

</script>
</body>
</html>
