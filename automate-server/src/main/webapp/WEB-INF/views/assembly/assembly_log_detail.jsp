<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
    <style>
        #lifeList li{
            padding: 5px;
            border-top: 1px solid #9a9a9a;
            cursor: pointer;
        }
        #lifeList li.current{
            background-color: #01a6b2;
            color: #ffffff;
        }
        #lifeList li:last-child{
            border-bottom: 1px solid #9a9a9a;
        }
        #lifeList li.head{
            font-weight: bold;
        }
        #lifeList li.step {
            padding-left: 20px;
        }

    </style>
</head>
<body class="has-js">

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-2">
            <section class="panel clearfix">
                <header class="panel-heading clearfix">
                    步骤
                </header>
                <div class="panel-body">
                    <ul id="lifeList">

                    </ul>
                </div>
            </section>
        </div>
        <div class="col-lg-10">
            <section class="panel clearfix">
                <header class="panel-heading clearfix">
                    步骤信息 (暂时偷个懒先这么看着 ^_^)
                </header>
                <div class="panel-body">
                    <pre id="stepConfig"></pre>
                </div>
            </section>
            <section class="panel clearfix">
                <header class="panel-heading clearfix">
                    执行明细
                </header>
                <div class="panel-body">
                    <pre id="taskContent"></pre>
                </div>
            </section>


        </div>
    </div>
    <!-- page end-->
</section>

<script id="stepTemplate" type="text/html">
    {{each lifeArray item i}}

        <li class="head">【{{i+1}}】 {{item.lifeName}}</li>
        {{each item.steps step j}}
        <li class="step" data-life="{{i}}" data-step="{{j}}">
            {{j+1}}. {{step.stepName}}
        </li>
        {{/each}}

    {{/each}}
</script>


<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var id = '${id}';

    var lifeArray;

    var taskLogCache;

    var showDetail = function(i, j){
        if(lifeArray && taskLogCache){

            $('#lifeList > li.step[data-life='+i+'][data-step='+j+']').addClass('current');

            var config = lifeArray[i].steps[j];
            $('#stepConfig').html(JSON.stringify(config));
            console.log(taskLogCache);
            $('#taskContent').html(taskLogCache[i+'_'+j].content);
        }
    };

    var onReceive = function(){
        if(lifeArray && taskLogCache){

            // $("#default-titles").html(template('stepTemplate', msg));
            // $("#taskContent").html(msg.data[0].content);
            //

            $("#lifeList > li.step").click(function(){
                var t = $(this);
                if(!t.hasClass('current')){
                    $("#lifeList > li.step.current").removeClass('current');

                    var i = t.attr('data-life');
                    var j = t.attr('data-step');
                    showDetail(i, j);
                }
            });

            showDetail(0, 0);
        }

    };

    $(function () {
        Core.get('/api/assembly/assemblyLineLog/'+id, null, function (msg) {
            console.log('流水线执行记录', msg);
            if (msg.status == 200 && msg.data) {

                lifeArray = JSON.parse(msg.data.config);

                $("#lifeList").html(template('stepTemplate', {lifeArray: lifeArray}));
                onReceive();
            }
        });

        Core.get('/api/assembly/assemblyLineTaskLogs', {id:id}, function (msg) {
            console.log('流水线任务执行明细列表', msg);
            if (msg.data.length > 0) {

                taskLogCache = {};
                for (var i = 0; i < msg.data.length; i++) {
                    var item = msg.data[i];
                    taskLogCache[item.lifeCycle+'_'+item.taskIndex] = item;
                }
                onReceive();
            }
        });
    });
</script>
</body>
</html>
