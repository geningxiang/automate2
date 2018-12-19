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
    <iframe id="main-content" src="http://www.fcaimao.com">
    </iframe>
    <!--main content end-->
    <!-- Right Slidebar start -->
    <jsp:include page="layout/right_slidebar.jsp"/>
    <!-- Right Slidebar end -->

</section>

<!-- js placed at the end of the document so the pages load faster -->
<script src="/resources/js/jquery.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="/resources/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="/resources/js/jquery.scrollTo.min.js"></script>
<script src="/resources/js/jquery.nicescroll.js" type="text/javascript"></script>
<script src="/resources/js/jquery.sparkline.js" type="text/javascript"></script>
<script src="/resources/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
<script src="/resources/js/owl.carousel.js" ></script>
<script src="/resources/js/jquery.customSelect.min.js" ></script>
<script src="/resources/js/respond.min.js" ></script>

<!--right slidebar-->
<script src="/resources/js/slidebars.min.js"></script>

<!--common script for all pages-->
<script src="/resources/js/common-scripts.js"></script>


</body>
</html>
