<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
    <style>
        .step-ul li {
            width: 100%;
            height: 32px;
            line-height: 32px;
            border: 1px solid #DCDEE3;
            border-radius: 3px;
            padding: 0 30px 0 10px;
            margin: 10px 0;
            overflow: hidden;
            position: relative;
        }

        .step-ul li.active {
            background-color: rgb(0, 193, 222);
            border-color: rgb(0, 193, 222);
            color: #fff;
        }

        .step-ul li i {
            position: absolute;
            top: 0;
            right: 10px;
            line-height: 32px;
            font-size: 18px;
            cursor: pointer;
        }
    </style>
</head>
<body class="has-js">

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    流水线
                </header>
                <div class="panel-body">
                    <form class="form-horizontal tasi-form" method="post" id="myForm">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">流水线名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">代码仓库ID</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="sourceCodeId">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">相关分支</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="branches">
                                <span class="help-block">正则表达式</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="remark"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">是否自动触发</label>
                            <div class="col-sm-10">
                                <input type="checkbox" name="autoTrigger" value="true" checked>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">定时</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="triggerCron">
                                <span class="help-block">cron表达式</span>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </div>

    </div>
    <div class="row">
        <div class="col-lg-2">
            <section class="panel clearfix">
                <div class="panel-body">
                    <ul class="step-ul" id="step-ul">

                    </ul>
                    <div class="btn-row">
                        <div class="btn-group">
                            <div class="btn-group">
                                <button data-toggle="dropdown" class="btn btn-success dropdown-toggle" type="button" aria-expanded="false"> 新增任务 <i class="fa fa-plus"></i> </button>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Maven任务</a></li>
                                    <li><a href="#">自定义Exec任务</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <div class="col-lg-10">
            <section class="panel clearfix">
                <header class="panel-heading">
                    任务内容
                </header>
                <div class="panel-body">
                    <form class="form-horizontal tasi-form" id="taskContent">

                    </form>
                </div>
            </section>
        </div>

    </div>

    <button id="doSubmit">保存</button>
    <!-- page end-->
</section>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script src="/resources/assets/assembly/assembly.js"></script>
<script>
$(function(){
    AssemblyUtil.init('[{"testSkip":true,"custom":"install","className":"com.automate.task.background.impl.MavenTask","clean":true,"locks":[]},{"testSkip":true,"custom":"install","className":"com.automate.task.background.impl.MavenTask","clean":true,"locks":[]},{"testSkip":true,"custom":"install","className":"com.automate.task.background.impl.MavenTask","clean":true,"locks":[]}]');
})

</script>
</body>
</html>
