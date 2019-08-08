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
                    更新包列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-success btn" onclick="showUpload()">
                        <i class="fa fa-plus"></i> 手动上传
                    </button>
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

    <!-- vertical center large Modal  start -->
    <div class="modal fade modal-dialog-center" id="uploadModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content-wrap">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">手动上传</h4>
                    </div>
                    <div class="modal-body">
                        <form id="packageUploadForm" class="form-horizontal tasi-form" method="post">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">项目</label>
                                <div class="col-sm-10">
                                    <input class="form-control" name="projectId" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">类型</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="type">
                                        <option value="1">全量更新</option>
                                        <option value="2">增量更新</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">备注</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" name="remark"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">文件</label>
                                <div class="col-sm-10">
                                    <input type="file" name="fileData" accept=".zip,.war,">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="progress">
                                    <div class="progress-bar" id="upload-progress-bar"></div>
                                </div>
                                <p id="upload-progress-info" style="padding: 10px; text-align: right;"></p>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success" type="button" onclick="startUpload()">开始上传</button>
                    </div>
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
            <th>类型</th>
            <th>分支</th>
            <th>commitId</th>
            <th>备注</th>
            <th>创建时间</th>
            <th>文件类型</th>
            <th>sha256</th>
            <th>上传人</th>
            <th>操作</th>
        </tr>
        {{each content item i}}
        <tr>
            <td>{{item.id}}</td>
            <td>【{{item.projectId}}】</td>
            <td>
                {{ if item.type == 1 }}
                全量更新
                {{ else if item.type == 2 }}
                增量更新
                {{ /if}}
            </td>
            <td>{{item.branch}}</td>
            <td>{{item.commitId}}</td>
            <td>{{item.remark}}</td>
            <td>{{dateFormat(item.createTime, 'yyyy-MM-dd HH:mm:ss')}}</td>
            <td>{{item.suffix}}</td>
            <td>{{item.sha256}}</td>
            <td>{{ item.userId || '后台构建' }}</td>
            <td>
                <button class="btn btn-success btn-xs" onclick="downFileListTxt('{{item.sha256}}')">
                    <i class="fa fa-files-o"></i> 下载文件列表
                </button>

                <button class="btn btn-success btn-xs" onclick="">
                    <i class="fa fa-hand-o-right"></i> 申请更新
                </button>
            </td>
        </tr>
        {{/each}}
    </table>
    {{@pageFooter($data)}}
</script>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script src="/resources/js/html5uploader.js"></script>
<script>

    var cacheMap = {};
    var pageNo = 1;
    var pageSize = 10;

    function queryList() {
        Core.get('/api/projectPackages', {pageNo: pageNo, pageSize: pageSize}, function (msg) {
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

    function downFileListTxt(sha256){
        if(sha256)
        window.open('/api/download/fileListSha/txt/'+sha256);
    }

    function showUpload() {
        $('#uploadModal').modal('show');
    }

    var loading = false;

    function startUpload() {
        var validResult = $("#packageUploadForm").valid();
        console.log('validateResult', validResult);
        if(!validResult){return;}
        var formData = new FormData();
        var formArray = $('#packageUploadForm *[name]')
        for (var i = 0; i < formArray.length; i++) {
            var item = formArray[i];
            if (item.type == 'file') {
                if (item.files.length > 0) {
                    if (item.files[0].size > 209715200) {
                        alert("上传文件不能大于200M");
                        return;
                    }
                    //只允许1个文件
                    formData.append(item.name, item.files[0]);
                } else {
                    alert("请选择要上传的更新文件");
                    return;
                }
            } else {
                // if (item.name == 'remark' && item.value.length < 10) {
                //     alert("请填写描述，且不得小于10个字");
                //     return;
                // }
                formData.append(item.name, item.value);
            }
        }
        if (loading) {
            alert("请勿重复提交");
            return;
        }
        loading = true;

        $.html5uploader({
            url: '/api/packageUpload',
            data: formData,
            onUploadStart: function () {

            },
            onUploadProgress: function (loaded, total, loadedStr, totalStr) {
                console.log(loaded, total);
                $("#upload-progress-info").html(loadedStr + ' / ' + totalStr);
                $("#upload-progress-bar").width(100 * loaded / total + "%");
            },
            onUploadSuccess: function (data) {
                data = JSON.parse(data);
                console.log(data);
                alert(data.msg);
                if (data.status == 200) {
                    queryList();
                    $('#uploadModal').modal('hide');
                }
            },
            onUploadComplete: function () {
                loading = false;
            },
            onUploadError: function (data) {
                alert(data);
            }
        });
    }
</script>
</body>
</html>
