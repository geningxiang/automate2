<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
    <link href="/resources/assets/prism/prism.css" rel="stylesheet">
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
                    <li id="default-title-0" class="current-step">
                        <div>Step1</div>
                        <span> </span></li>
                    <li id="default-title-1" class="">
                        <div>Step 2</div>
                        <span> </span></li>
                    <li id="default-title-2" class="">
                        <div>Step 3</div>
                        <span> </span></li>
                </ul>
            </div>
            <article>
                <pre class="line-numbers">
                    <!--language-javastacktrace-->
                <code class="language-shell">
                Exception in thread "BackgroundTask-Thread-7" java.lang.RuntimeException: 报错啦
                at com.automate.task.background.BackgroundAssemblyManagerTest$2.run(BackgroundAssemblyManagerTest.java:53)
                at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
                at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
                at java.lang.Thread.run(Thread.java:744)

                2019-02-02 16:47:27,233 [INFO] com.automate.exec.ExecHelper#exec(53) - cmd.exe /c mvn clean package -DskipTests=true
                mvn clean package -DskipTests=true
                [INFO] Scanning for projects...
                [INFO]
                [INFO] ---------------------------< com.genx:demo >----------------------------
                [INFO] Building demo 0.0.1-SNAPSHOT
                [INFO] --------------------------------[ jar ]---------------------------------
                [INFO]
                [INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ demo ---
                [INFO]
                [INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ demo ---
                [INFO] Using 'UTF-8' encoding to copy filtered resources.
                [INFO] Copying 1 resource
                [INFO] Copying 0 resource
                [INFO]
                [INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ demo ---
                [INFO] Changes detected - recompiling the module!
                [INFO] Compiling 1 source file to D:\github-workspace\SpringBootDemo\target\classes
                [INFO]
                [INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ demo ---
                [INFO] Using 'UTF-8' encoding to copy filtered resources.
                [INFO] skip non existing resourceDirectory D:\github-workspace\SpringBootDemo\src\test\resources
                [INFO]
                [INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ demo ---
                [INFO] Changes detected - recompiling the module!
                [INFO] Compiling 1 source file to D:\github-workspace\SpringBootDemo\target\test-classes
                [INFO]
                [INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ demo ---
                [INFO] Tests are skipped.
                [INFO]
                [INFO] --- maven-jar-plugin:3.1.1:jar (default-jar) @ demo ---
                [INFO] Building jar: D:\github-workspace\SpringBootDemo\target\demo-0.0.1-SNAPSHOT.jar
                [INFO]
                [INFO] --- spring-boot-maven-plugin:2.1.2.RELEASE:repackage (repackage) @ demo ---
                [INFO] Replacing main artifact with repackaged archive
                [INFO] ------------------------------------------------------------------------
                [INFO] BUILD SUCCESS
                [INFO] ------------------------------------------------------------------------
                [INFO] Total time: 3.632 s
                [INFO] Finished at: 2019-02-02T16:47:31+08:00
                [INFO] ------------------------------------------------------------------------
                finished 0

                </code>
                </pre>
            </article>
        </div>
    </section>
    <!-- page end-->
</section>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script src="/resources/assets/prism/prism.js"></script>
<script>
    var id = '${id}';


</script>
</body>
</html>
