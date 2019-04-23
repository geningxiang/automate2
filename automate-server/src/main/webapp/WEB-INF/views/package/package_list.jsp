<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    更新包列表
                </header>
                <div class="panel-body" id="package-list-content">
                </div>
            </section>
        </div>
    </div>
    <!-- page end-->

    <!-- vertical center large Modal  start -->
    <div class="modal fade modal-dialog-center" id="fileListModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content-wrap">
                <div class="modal-content" id="fileListModelContent">

                </div>
            </div>
        </div>
    </div>
    <!-- vertical center large Modal end -->
</section>


<script id="packageListTemplate" type="text/html">
    <table class="table table-hover p-table">
        <tr>
            <th>ID</th>
            <th>项目</th>
            <th>分支</th>
            <th>commitId</th>
            <th>备注</th>
            <th>创建时间</th>
            <th>文件类型</th>
            <th>操作</th>
        </tr>
        {{each content item i}}
        <tr>
            <td>{{item.id}}</td>
            <td>【{{item.sourceCodeId}}】</td>
            <td>{{item.branch}}</td>
            <td>{{item.commitId}}</td>
            <td>{{item.remark}}</td>
            <td>{{dateFormat(item.createTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{item.packageType}}</td>
            <td>
                <button class="btn btn-success btn-xs" onclick="showFileList('{{item.id}}')"><i
                        class="fa fa-files-o"></i> 文件列表
                </button>
            </td>
        </tr>
        {{/each}}
    </table>
    {{@pageFooter($data)}}
</script>

<script id="fileListModelContentTemplate" type="text/html">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">{{packagePath}}</h4>
    </div>
    <div class="modal-body">
        <table class="table table-advance table-hover">
            <thead>
            <tr>
                <th>路径</th>
                <th>MD5</th>
                <th>大小</th>
            </tr>
            </thead>
            <tbody>
            {{each fileArray item i}}
            <tr>
                <td><p>{{item.path}}</p></td>
                <td><p>{{item.md5}}</p></td>
                <td><p>{{item.size}}</p></td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </div>
</script>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>

    var cacheMap = {};
    var pageNo = 1;
    var pageSize = 10;

    function queryList() {
        Core.get('/api/package/list', {pageNo: pageNo, pageSize: pageSize}, function (msg) {
            console.log('更新包列表', msg);
            cacheMap = {};

            var item;
            for (var i = 0; i < msg.data.content.length; i++) {
                item = msg.data.content[i];
                cacheMap[item.id] = item;
            }

            $("#package-list-content").html(template('packageListTemplate', msg.data));
            msg.data.content = null;
        });
    }

    function changePage(n) {
        pageNo = n;
        queryList();
    }

    function changePageSize(val) {
        if (val) {
            pageNo = 1;
            pageSize = val;
            queryList();
        }
    }

    $(function () {
        queryList();
    });

    function showFileList(id) {
        var item = cacheMap[id];
        console.log('143', item);
        if (item) {
            var fileArray = JSON.parse(item.fileTree);
            $("#fileListModelContent").html(template('fileListModelContentTemplate', {
                packagePath: item.packagePath,
                fileArray: fileArray
            }));

            $('#fileListModal').modal('show');
        }

    }
</script>
</body>
</html>
