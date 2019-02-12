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
                    代码仓库列表
                </header>
                <table class="table table-hover p-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>名称</th>
                        <th>备注</th>
                        <th>VCS地址</th>
                        <th><i class=" fa fa-edit"></i> Status</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(list)==0}">
                        <tr>
                            <td align="center" colspan="6">没有找到相关的记录！</td>
                        </tr>
                    </c:if>
                    <c:forEach var="_item" items="${list}" varStatus="status">
                        <tr>
                            <td>${_item.id}</td>
                            <td>${_item.name}</td>
                            <td>${_item.remark}</td>
                            <td>
                                <i class="fa fa-github"></i> &nbsp;&nbsp;${_item.vcsUrl}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${_item.status == 1}">
                                        <span class="label label-success label-mini">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-warning label-mini">Closed</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${_item.createTime}"/>
                            </td>
                            <td>
                                <button class="btn btn-primary btn-xs" onclick="sourceCodeDetail(${_item.id})"><i
                                        class="fa fa-eye"></i> 详情
                                </button>
                                &nbsp;&nbsp;
                                <button class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                &nbsp;&nbsp;
                                <button class="btn btn-warning btn-xs" onclick="sourceCodeSync(${_item.id})"><i
                                        class="fa  fa-refresh"></i> 同步
                                </button>
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
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    $(function () {


    });

    function sourceCodeDetail(id) {
        if (id && id > 0)
            window.open("/admin/sourcecode/detail?id=" + id);

    }

    function sourceCodeSync(id) {
        $.post("/api/sourcecode/sync", {id: id}, function (data) {
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
