<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
</head>
<body class="has-js">

<section class="wrapper site-min-height">
    <!-- page start-->
    <section class="panel">
        <header class="panel-heading clearfix">
            <h3 class="pull-left">流水线运行明细</h3>
        </header>
        <div class="panel-body">
            <div class="stepy-tab">
                <ul id="default-titles" class="stepy-titles clearfix">


                </ul>
            </div>
            <article>
                <pre class="line-numbers">
                    <!--language-javastacktrace-->
                <code class="language-shell" id="taskContent"></code>
                </pre>
            </article>
        </div>
    </section>
    <!-- page end-->
</section>

<script id="stepTemplate" type="text/html">
    {{each data item i}}
    <li class="{{i == 0 ? 'current-step' : ''}}" data-index="{{i}}">
        <div>Step{{i+1}}</div>
        <span> </span>
    </li>
    {{/each}}
</script>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    var id = '${id}';

    var tempArray = [];
    $(function () {
        Core.post('/api/assembly/logDetail', {id: id}, function (msg) {
            console.log('任务明细', msg);
            if (msg.data.length > 0) {
                $("#default-titles").html(template('stepTemplate', msg));
                $("#taskContent").html(msg.data[0].content);

                tempArray = msg.data;

                $("#default-titles > li").click(function(){
                    $("#default-titles > li.current-step").removeClass('current-step');

                    $(this).addClass('current-step');

                    $("#taskContent").html(msg.data[$(this).attr("data-index")].content);
                });
            }


        });
    });
</script>
</body>
</html>
