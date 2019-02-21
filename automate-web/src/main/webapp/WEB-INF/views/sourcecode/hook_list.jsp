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
                <div class="panel-body">
                    <div class="adv-table">
                        <div id="hookListContent" class="dataTables_wrapper form-inline" role="grid">

                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <!-- page end-->
</section>
<script id="hookListTemplate" type="text/html">
    <table class="table table-advance table-hover">
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
        {{if content}}
            {{each content item i}}
            <tr>
                <td>{{item.id}}</td>
                <td>{{item.source}}</td>
                <td>{{item.createTime}}</td>
                <td>{{item.event}}</td>
                <td>{{item.delivery}}</td>
                <td>
                    {{if item.handleStatus == 1}}
                    <span class="label label-success label-mini">{{item.handleResult}}</span>
                    {{else}}
                    <span class="label label-warning label-mini">{{item.handleResult}}</span>
                    {{/if}}
                </td>
                <td>
                    {{if item.projectId && item.projectId > 0}}
                    【{{item.projectId}}】
                    {{/if}}
                </td>
            </tr>

            {{/each}}
        {{else}}
            <tr><td align="center" colspan="6">没有找到相关的记录！</td></tr>
        {{/if}}
        </tbody>
    </table>
    {{@pageFooter($data)}}
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>

    var pageNo = 1;
    var pageSize = 20;
    $(function(){
        query();
    });

    function query() {
        Core.post('/api/sourcecode/hookList', {pageNo: pageNo, pageSize: pageSize}, function(msg){

            $("#hookListContent").html(template('hookListTemplate', msg.data));
        });
    }

    function changePage(n){
        pageNo = n;
        query();
    }

    function changePageSize(val){
        console.log('changePageSize', val);
        if(val){
            pageNo = 1;
            pageSize = val;
            query();
        }
    }
</script>
</body>
</html>
