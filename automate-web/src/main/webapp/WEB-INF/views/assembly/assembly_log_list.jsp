<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    流水线执行记录
                </header>
                <form id="qryFrmId" name="qryFrm" class="query-form" action="/admin/assambly/assamblyLogList" method="post"
                      data-target="#hookLogTable">
                    <input type="hidden" name="pageNo" id="pageNo" value="${pager.number + 1}"/>
                    <input type="hidden" name="pageSize" id="pageSize" value="${pager.size}"/>
                    <input type="hidden" name="totalElements" id="totalElements" value="${pager.totalElements}"/>
                </form>
                <div class="panel-body">
                    <table class="table table-striped table-advance table-hover">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>源码仓库</th>
                            <th>流水线</th>
                            <th>分支</th>
                            <th>commitId</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>状态</th>
                            <th>操作</th>
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
                                <td>
                                    【${_item.sourceCodeId}】${sourceCodeMap[_item.sourceCodeId].name}
                                </td>
                                <td>
                                    【${_item.assemblyLineId}】${assemblyLineMap[_item.assemblyLineId].name}
                                </td>
                                <td>${_item.branch}</td>
                                <td>${_item.commitId}</td>
                                <td>
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.startTime}"/>
                                </td>
                                <td>
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.endTime}"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${_item.status == 0}">
                                            排队中
                                        </c:when>
                                        <c:when test="${_item.status == 1}">
                                            running
                                        </c:when>
                                        <c:when test="${_item.status == 2}">
                                            已取消
                                        </c:when>
                                        <c:when test="${_item.status == 3}">
                                            出错啦
                                        </c:when>
                                        <c:when test="${_item.status == 4}">
                                            已完成
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <button class="btn btn-info btn-xs" onclick="assemblyLogDetail(${_item.id})"><i
                                            class="fa fa-eye"></i> 详情
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
    <!-- page end-->
</section>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>

    function assemblyDetail(id) {
        if (id && id > 0)
            window.open("/admin/assembly/detail?id=" + id);
    }

    function assemblyLogDetail(assemblyLineLogId){
        window.open('/admin/assembly/assemblyLogDetail?id=' + assemblyLineLogId||'');
    }
</script>
</body>
</html>
