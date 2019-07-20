<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:useBean id="timestamp" class="java.util.Date"/>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
    <style>
        .time {
            white-space: nowrap;

        }
    </style>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    分支: ${branchName} 提交记录
                </header>
                <table class="table table-advance table-hover">
                    <thead>
                    <tr>
                        <th>提交者</th>
                        <th>提交时间</th>
                        <th>版本号</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(commitLogs)==0}">
                        <tr>
                            <td align="center" colspan="7">没有找到相关的记录！</td>
                        </tr>
                    </c:if>
                    <c:forEach var="_item" items="${commitLogs}" varStatus="status">
                        <tr>
                            <td>${_item.committer.name}</td>
                            <td class="time">
                                <jsp:setProperty name="timestamp" property="time" value="${_item.commitTime}"/>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${timestamp}"/>
                            </td>
                            <td>${_item.id}</td>
                            <td><pre>${_item.msg}</pre></td>
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

</script>
</body>
</html>
