<%@ page pageEncoding="UTF-8" %>
<aside>
    <div id="sidebar" class="nav-collapse ">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu" id="nav-accordion">
        <li>
            <a class="active" href="/admin/dashboard.do" target="mainContent"> <i class="fa fa-dashboard"></i> <span>面板</span> </a>
        </li>

        <li class="sub-menu">
            <a href="javascript:;"> <i class="fa fa-code"></i> <span>代码</span> </a>
            <ul class="sub">
                <li><a href="/admin/sourcecode/list" target="mainContent">代码仓库</a></li>
                <li><a href="/admin/sourcecode/branchList" target="mainContent">分支列表</a></li>
            </ul>
        </li>

        <li class="sub-menu">
        <a href="javascript:;"> <i class="fa fa-laptop"></i> <span>服务器</span> </a>
        <ul class="sub">
        <li><a href="/admin/server/list.do" target="mainContent">服务器列表</a></li>
        </ul>
        </li>
        <li class="sub-menu"> <a href="javascript:;"> <i class="fa fa-cogs"></i> <span>管理面板</span> </a>
        <ul class="sub">
        <li><a href="#">控制面板</a></li>
        <li><a href="#">用户管理</a></li>
        <li><a href="#">组织管理</a></li>
        <li><a href="#">应用监控</a></li>
        <li><a href="#">系统配置</a></li>
        </ul> </li>
        </ul>
        <!-- sidebar menu end-->
        </div>
        </aside>
