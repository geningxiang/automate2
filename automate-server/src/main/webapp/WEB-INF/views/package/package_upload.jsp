<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>上传更新文件</title>
    <jsp:include page="../common/head.jsp"/>
</head>
<body>
<section class="wrapper">
    <div class="row">
        <div class="col-md-12">
            <section class="panel">
                <header class="panel-heading">
                    <span class="label label-primary">上传更新文件</span>
                </header>
                <div class="panel-body">
                    <form id="myForm" action="" class="form-horizontal tasi-form">

                        <table class="table table-bordered td-middle">
                            <tr>
                                <th style="width: 100px;">所属项目</th>
                                <th>
                                    <input type="number" class="form-control" name="projectId" maxlength="10"/>
                                </th>
                            </tr>
                            <tr>
                                <th>更新类型</th>
                                <td>
                                    <select name="type" class="form-control">
                                        <option value="0">增量</option>
                                        <option value="1">全量</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>版本</th>
                                <td>
                                    <input class="form-control" name="version" maxlength="10"/>
                                </td>
                            </tr>
                            <tr>
                                <th>更新描述</th>
                                <td>
                                    <textarea class="form-control" name="remark" maxlength="200"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th>选择文件</th>
                                <td>
                                    <input type="file" name="fileData" accept=".zip,.war,"/>
                                </td>
                            </tr>
                            <tr>
                                <th>进度</th>
                                <td>
                                    <p style="text-align: right" id="upload-progress-info"></p>
                                    <div class="progress progress-striped active progress-sm">
                                        <div class="progress-bar progress-bar" id="upload-progress-bar">
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>

                <div style="width: 100%; text-align: center; padding-bottom: 20px;">
                    <button type="button" class="btn btn-success" onclick="save()">确认上传</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-warning" onclick="window.close()">取消关闭</button>
                </div>
            </section>
        </div>
    </div>
</section>
</body>

<jsp:include page="../common/common_js.jsp"></jsp:include>
<script type="text/javascript" src="/resources/js/html5uploader.js"></script>
<script type="text/javascript">
    var loading = false;
    function save() {

        var formData = new FormData();
        var formArray = $('#myForm *[name]')
        for (var i = 0; i < formArray.length; i++) {
            var item = formArray[i];
            if(item.type == 'file') {
                if(item.files.length > 0) {
                    if(item.files[0].size > 209715200) {
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
                if(item.name == 'explain' && item.value.length < 10) {
                    alert("请填写描述，且不得小于10个字");
                    return;
                }
                formData.append(item.name, item.value);
            }
        }
        if(loading) {
            alert("请勿重复提交");
            return;
        }
        loading = true;
        $.html5uploader({
            url: '/api/projectPackage',
            data: formData,
            onUploadStart: function () {

            },
            onUploadProgress: function (loaded, total, loadedStr, totalStr) {
                console.log(loaded, total);
                $("#upload-progress-info").html(loadedStr + ' / ' + totalStr);
                $("#upload-progress-bar").width( 100 * loaded / total + "%");
            },
            onUploadSuccess: function (data) {
                console.log(data);
                data = JSON.parse(data);
                alert(data.msg);
                if(data.status == 200) {
                    window.opener && window.opener.doQuery();
                    window.close();
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
</html>