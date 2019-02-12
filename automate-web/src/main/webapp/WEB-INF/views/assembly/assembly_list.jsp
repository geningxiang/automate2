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
                    流水线列表
                </header>
                <div class="panel-body">
                    <div class="clearfix">
                        <div class="btn-group">
                            <button id="editable-sample_new" class="btn green" onclick="window.open('/admin/assembly/detail')">
                                Add New <i class="fa fa-plus"></i>
                            </button>
                        </div>

                    </div>

                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th>源码仓库</th>
                        <th>流水线名称</th>
                        <th>备注</th>
                        <th>关联分支</th>
                        <th>是否自动触发</th>
                        <th>定时</th>
                        <th>最后触发时间</th>
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
                                【${_item.sourceCodeId}】${sourceCodeMap[_item.sourceCodeId].name}
                            </td>
                            <td>${_item.name}</td>
                            <td>${_item.remark}</td>
                            <td>${_item.branches}</td>
                            <td>
                                ${_item.autoTrigger}
                            </td>
                            <td>
                                ${_item.triggerCron}
                            </td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.lastRunTime}" />
                            </td>
                            <td>
                                <button class="btn btn-info btn-xs" onclick="assemblyDetail(${_item.id})"><i class="fa fa-pencil"></i> 编辑</button>
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
        if(id && id > 0)
            window.open("/admin/assembly/detail?id=" + id);
    }
</script>
</body>
</html>
