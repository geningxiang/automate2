<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:useBean id="timestamp" class="java.util.Date"/>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading"><h3>项目详情</h3></header>
                <div id="sourceCodeDetail" class="panel-body form-horizontal tasi-form">
                </div>
            </section>
        </div>
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading"><h3>分支列表</h3></header>
                <div id="branchList" class="panel-body">

                </div>
            </section>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading clearfix">
                    <h3 class="pull-left">流水线列表</h3>
                    <div class="pull-right">
                        <a class="btn btn-info" style="margin-top: 20px;" href="/admin/assembly/detail?projectId=${id}" target="_blank">
                            <i class="fa fa-plus"></i> 创建流水线
                        </a>
                    </div>
                </header>
                <div id="assemblyList" class="panel-body form-horizontal tasi-form">
                </div>
            </section>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    <h3>流水线运行记录</h3>
                </header>
                <div id="assemblyLogList" class="panel-body form-horizontal tasi-form">
                </div>
            </section>
        </div>
    </div>
    <!-- page end-->
</section>

<!-- vertical center large Modal  start -->
<div class="modal fade modal-dialog-center" id="assemblyRunContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content-wrap">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">运行流水线</h4>
                </div>
                <div class="modal-body">
                    <form id="assemblyRunForm" class="form-horizontal tasi-form" method="post">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">流水线名称</label>
                            <div class="col-sm-10">
                                <input class="form-control" type="hidden" id="assemblyId" name="id" />
                                <input class="form-control" id="assemblyName" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">分支</label>
                            <div class="col-sm-10">
                                <input class="form-control" name="branchName" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">版本号</label>
                            <div class="col-sm-10">
                                <input class="form-control" name="commitId" placeholder="默认最新版本"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                    <button class="btn btn-success" type="button" onclick="startRun()">开始执行</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- vertical center large Modal end -->

<script id="sourceCodeDetailTemplate" type="text/html">
    <div class="form-group">
        <label class="col-lg-3 control-label">名称</label>
        <div class="col-lg-9">
            {{name}}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">备注</label>
        <div class="col-lg-9">
            {{remark}}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">仓库地址</label>
        <label class="col-lg-9 control-label">
            {{vcsUrl}}
        </label>
    </div>
</script>

<script id="branchListTemplate" type="text/html">

    <table class="table table-hover p-table">
        <tr>
            <th>分支名</th>
            <th>SHA1</th>
            <th>最后提交时间</th>
            <th>提交者</th>
        </tr>
        {{each data item i}}
        <tr>
            <td>{{item.branchName}}</td>
            <td>{{item.lastCommitId}}</td>
            <td>{{item.lastCommitTime}}</td>
            <td>{{item.lastCommitUser}}</td>
        </tr>
        {{/each}}
    </table>
</script>

<!-- 流水线列表模板 -->
<script id="assemblyListTemplate" type="text/html">
    <table class="table table-hover p-table">
        <tr>
            <th>名称</th>
            <th>相关分支</th>
            <th>自动触发</th>
            <th>定时</th>
            <th>操作</th>
        </tr>
        {{if data.length == 0}}
        <tr><td class="text-center" colspan="5">暂无相关记录</td></tr>
        {{/if}}
        {{each data item i}}
        <tr>
            <td>【{{item.id}}】{{item.name}}</td>
            <td>{{item.branches}}</td>
            <td>{{item.autoTrigger ? '是' : '否'}}</td>
            <td>{{item.triggerCron}}</td>
            <td>
                <button class="btn btn-info btn-xs" onclick="assemblyDetail('{{item.id}}')"><i class="fa fa-play"></i> 编辑</button>
                &nbsp;&nbsp;
                <button class="btn btn-success btn-xs" onclick="showRun('{{item.id}}', '{{item.name}}')"><i class="fa  fa-cog"></i> 运行</button>
            </td>
        </tr>
        {{/each}}
    </table>
</script>

<!-- 流水线运行记录模板 -->
<script id="assemblyLogListTemplate" type="text/html">
    <table class="table table-hover p-table">
        <tr>
            <th>流水线</th>
            <th>分支</th>
            <th>commitId</th>
            <th>创建时间</th>
            <th>状态</th>
            <th>开始时间</th>
            <th>耗时</th>
            <th>操作</th>
        </tr>
        {{each content item i}}
        <tr>
            <td>【{{item.assemblyLineId}}】{{item.assemblyLineName}}</td>
            <td>{{item.branch}}</td>
            <td>{{item.commitId}}</td>
            <td>{{dateFormat(item.createTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>
                {{if item.status == 0}}等待执行
                {{else if item.status == 1}}执行中
                {{else if item.status == 2}}已取消
                {{else if item.status == 3}}出错了
                {{else if item.status == 4}}已完成
                {{/if}}
            </td>
            <td>{{dateFormat(item.startTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{if item.endTime && item.startTime}}{{item.endTime - item.startTime}}毫秒{{/if}}</td>
            <td><button class="btn btn-warning btn-xs" onclick="showLogDetail('{{item.id}}')"><i class="fa fa-list-ol"></i> 详细记录</button></td>
        </tr>
        {{/each}}
    </table>
    {{@pageFooter($data)}}
</script>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var id = '${id}';
    $(function(){
        init();
    });

    function init() {
        querySourceCodeDetail();
        queryBranchList();
        queryAssemblyList();
    }

    function querySourceCodeDetail(){
        Core.get('/api/project/' + id, null, function(msg){
           console.log('查询项目详情', msg);
           $("#sourceCodeDetail").html(template('sourceCodeDetailTemplate', msg.data));
        });
    }

    function queryBranchList(){
        Core.get('/api/projectBranches', {id: id}, function(msg){
            console.log('分支列表', msg);
            $("#branchList").html(template('branchListTemplate', msg));
        });
    }

    function queryAssemblyList(){
        Core.get('/api/assembly/assemblyLines', {projectId: id}, function(msg){
            console.log('流水线', msg);
            $("#assemblyList").html(template('assemblyListTemplate', msg));

            queryAssemblyLogList();
        });
    }

    var pageNo = 1;
    var pageSize = 10;
    function queryAssemblyLogList(){
        Core.get('/api/assembly/assemblyLineLogs', {projectId: id, pageNo: pageNo, pageSize: pageSize}, function(msg){
            console.log('流水线执行记录', msg);
            $("#assemblyLogList").html(template('assemblyLogListTemplate', msg.data));
        });
    }

    function changePage(n){
        pageNo = n;
        queryAssemblyLogList();
    }

    function changePageSize(val){
        if(val){
            pageNo = 1;
            pageSize = val;
            queryAssemblyLogList();
        }
    }


    function showRun(assemblyLineId, name){
        $("#assemblyId").val(assemblyLineId);
        $("#assemblyName").val(name);
        $("#assemblyRunContent").modal('show');
    }

    function startRun(){
        if($("#assemblyRunForm").valid()){
            Core.post('/api/assembly/startAssemblyLine', $("#assemblyRunForm").serializeData(), function(msg){
                console.log('运行流水线', msg);
                if(msg.status == 200){
                    $("#assemblyRunContent").modal('hide');
                    alert("开始执行:您可以先去逛逛,一会在回来看看呗");
                    changePage(1);
                } else {
                    alert(msg.msgs);
                }

            });
        }
    }

    function showLogDetail(assemblyLineLogId){
        window.open('/admin/assembly/assemblyLogDetail?id=' + assemblyLineLogId||'');
    }

    function assemblyDetail(id) {
        if(id && id > 0)
            window.open("/admin/assembly/detail?id=" + id);
    }
</script>
</body>
</html>
