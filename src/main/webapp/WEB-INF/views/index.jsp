<html>
<head>
    <jsp:include page="common/head.jsp"/>
    <style>
        #container{
            padding: 0;
        }
        #main-content{
            width: 100%;
            height: 100%;
            box-sizing: border-box;
            padding: 60px 0 0 210px;
            margin: 0;
            border: none;
        }
        @media (max-width: 768px) {
            #main-content {
                padding-left: 0 !important;
            }
        }
    </style>
</head>
<body>

<section id="container">
    <!--header start-->
    <jsp:include page="layout/top.jsp"/>
    <!--header end-->

    <!--sidebar start-->
    <jsp:include page="layout/left.jsp"/>
    <!--sidebar end-->

    <!--main content start-->
    <iframe id="main-content" src="/admin/dashboard.do">
    </iframe>
    <!--main content end-->
    <!-- Right Slidebar start -->
    <jsp:include page="layout/right_slidebar.jsp"/>
    <!-- Right Slidebar end -->

</section>
<jsp:include page="common/common_js.jsp"/>
</body>
</html>
