<%@ page pageEncoding="UTF-8" %>
<aside>
    <div id="sidebar" class="nav-collapse ">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu" id="nav-accordion">
        <li>
            <a class="active" href="/admin/dashboard.do" target="mainContent"> <i class="fa fa-dashboard"></i> <span>面板</span> </a>
        </li>

        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-code"></i> <span>项目管理</span> </a>
            <ul class="sub">
                <li><a href="/admin/project/list" target="mainContent">项目列表</a></li>
                <li><a href="/admin/project/branchList" target="mainContent">分支列表</a></li>
                <li><a href="/admin/project/hookList" target="mainContent">hook日志</a></li>
            </ul>
        </li>
        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-dropbox"></i> <span>MAVEN</span> </a>
            <ul class="sub">
                <li><a href="/admin/mvn/index" target="mainContent">私有库</a></li>
                <li><a href="javascript:alert('开发中');" target="mainContent">deploy记录</a></li>
            </ul>
        </li>
        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-bullhorn"></i> <span>发布</span> </a>
            <ul class="sub">
                <li><a href="/admin/package/list" target="mainContent">包列表</a></li>
                <li><a href="/admin/application/updateApplyList" target="mainContent">更新申请记录</a></li>
                <li><a href="/admin/application/updateLogList" target="mainContent">更新记录</a></li>
            </ul>
        </li>
        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-twitter"></i> <span>任务</span> </a>
            <ul class="sub">
                <li><a href="/admin/background/dashboard" target="mainContent">任务中心</a></li>

                <li><a href="javascript:alert('开发中');" target="mainContent">任务日志</a></li>
            </ul>
        </li>

        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-laptop"></i> <span>服务器</span> </a>
            <ul class="sub">
                <li><a href="/admin/server/list.do" target="mainContent">服务器列表</a></li>
                <li><a href="/admin/application/list.do" target="mainContent">应用列表</a></li>
            </ul>
        </li>
        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-cogs"></i> <span>管理面板</span> </a>
            <ul class="sub">
                <li><a href="#">控制面板</a></li>
                <li><a href="#">人员管理</a></li>
                <li><a href="#">组织管理</a></li>
                <li><a href="#">应用监控</a></li>
                <li><a href="#">线程池</a></li>
                <li><a href="#">系统配置</a></li>
            </ul> </li>
        </ul>
        <!-- sidebar menu end-->
        </div>
        </aside>
