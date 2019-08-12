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

<section class="wrapper">
    <!-- page start-->

    <div class="row">
        <div class="col-sm-6">
            <section class="panel">
                <header class="panel-heading">
                    RUNNING  当前运行数${fn:length(runningList)}
                </header>
                <table class="table">
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th>STATUS</th>
                    </tr>
                    <tbody>
                        <c:if test="${fn:length(runningList)==0}">
                            <tr>
                                <td align="center" colspan="3">NO RUNNING</td>
                            </tr>
                        </c:if>
                        <c:forEach var="_item" items="${runningList}" varStatus="status">
                            <tr>
                                <td>${_item.uniqueId}</td>
                                <td>${_item.name}</td>
                                <td>${_item.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>

        <div class="col-sm-6">
            <section class="panel">
                <header class="panel-heading">
                    WAITING     当前等待数${fn:length(waitingList)}
                </header>
                <table class="table">
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                    </tr>
                    <tbody>
                    <c:if test="${fn:length(waitingList)==0}">
                        <tr>
                            <td align="center" colspan="3">NO WAITING</td>
                        </tr>
                    </c:if>
                    <c:forEach var="_item" items="${waitingList}" varStatus="status">
                        <tr>
                            <td>${_item.uniqueId}</td>
                            <td>${_item.name}</td>
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
