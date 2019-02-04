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
                    HOOK日志
                </header>
                <form id="qryFrmId" name="qryFrm" class="query-form" action="/admin/sourcecode/hookList" method="post" data-target="#hookLogTable">
                    <input type="hidden" name="pageNo" id="pageNo" value="${pager.number + 1}"/>
                    <input type="hidden" name="pageSize" id="pageSize" value="${pager.size}"/>
                    <input type="hidden" name="totalElements" id="totalElements" value="${pager.totalElements}"/>
                </form>
                <div class="panel-body">
                    <div class="adv-table">
                        <div id="hookListTable" class="dataTables_wrapper form-inline" role="grid">

                            <table id="hookLogTable" class="table table-advance table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>来源</th>
                                    <th>时间</th>
                                    <th>事件</th>
                                    <th>Delivery</th>
                                    <th>处理结果</th>
                                    <th>关联</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${pager.size == 0}">
                                    <tr>
                                        <td align="center" colspan="6">没有找到相关的记录！</td>
                                    </tr>
                                </c:if>
                                <c:forEach var="_item" items="${pager.content}" varStatus="status">
                                    <tr>
                                        <td>${_item.id}</td>
                                        <td>${_item.source}</td>
                                        <td>2019-02-02 18:00:00</td>
                                        <td>${_item.event}</td>
                                        <td>${_item.delivery}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${_item.handleStatus == 1}">
                                                    <span class="label label-success label-mini">${_item.handleResult}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-warning label-mini">${_item.handleResult}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td></td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
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
