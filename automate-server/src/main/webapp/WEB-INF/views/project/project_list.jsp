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

<section class="">
    <!-- page start-->

    <section class="panel">
        <header class="panel-heading clearfix">
            <h3 class="pull-left">项目列表</h3>
        </header>
    </section>


    <div class="wrapper panel-body" style="padding:15px 30px;">
        <div class="row antd-pro-pages-list-card-list-cardList">
            <div class="col-lg-4">
                <div class="ant-list-item-content ant-list-item-content-single">
                    <button type="button" class="ant-btn antd-pro-pages-list-card-list-newButton ant-btn-dashed" onclick="$('#createProjectModal').modal('show');">
                        <i aria-label="图标: plus" class="anticon anticon-plus">
                        <svg viewBox="64 64 896 896" class="" data-icon="plus" width="1em" height="1em"
                             fill="currentColor" aria-hidden="true">
                            <path d="M848 474H550V152h-76v322H176c-4.4 0-8 3.6-8 8v60c0 4.4 3.6 8 8 8h298v322h76V550h298c4.4 0 8-3.6 8-8v-60c0-4.4-3.6-8-8-8z"></path>
                        </svg>
                    </i><span> 新建项目</span></button>
                </div>
            </div>

            <div id="sourceCodeListBody">

            </div>
        </div>
    </div>
    <!-- page end-->

    <!-- vertical center large Modal  start -->
    <div class="modal fade modal-dialog-center" id="createProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content-wrap">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">新建项目</h4>
                    </div>
                    <div class="modal-body">
                        <form id="sourceCodeForm" class="form-horizontal tasi-form" method="post">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">项目名称</label>
                                <div class="col-sm-10">
                                    <input class="form-control" name="name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">备注</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" name="remark"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">项目类型</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="type" disabled>
                                        <option value="0">Java</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">编译方式</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="compileType" disabled>
                                        <option value="0">Maven</option>
                                        <option value="1">Gradle</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">版本控制</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="vcsType" >
                                        <option value="0">Git</option>
                                        <option value="1">SVN</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">代码仓库地址</label>
                                <div class="col-sm-10">
                                    <input class="form-control" name="vcsUrl" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">用户名</label>
                                <div class="col-sm-10">
                                    <input class="form-control" name="userName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input class="form-control" name="passWord">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-info pull-left" type="button" onclick="testConnection()"><i class="fa fa-refresh"></i> Test Connection</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                        <button class="btn btn-success" type="button" onclick="createProject()">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- vertical center large Modal end -->
</section>


<script id="sourceCodeListTemplate" type="text/html">
    {{if data }}
    {{each data item i}}
    <div class="col-lg-4">
        <div class="ant-card antd-pro-pages-list-card-list-card ant-card-bordered ant-card-hoverable">
            <div class="ant-card-body">
                <div class="ant-card-meta">
                    <div class="ant-card-meta-avatar">
                        <img alt="" src="/resources/img/language/java.png">
                    </div>
                    <div class="ant-card-meta-detail">
                        <div class="ant-card-meta-title">{{item.name}}</div>
                        <div class="ant-card-meta-description">
                            <div class="antd-pro-components-ellipsis-index-ellipsis antd-pro-pages-list-card-list-item antd-pro-components-ellipsis-index-lineClamp">
                                {{item.remark}}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <ul class="ant-card-actions">
                <li style="width: 50%;"><span><a href="javascript:sourceCodeDetail('{{item.id}}')">详情</a></span></li>
                <li style="width: 50%;"><span><a href="javascript:sourceCodeSync('{{item.id}}')">VCS同步</a></span></li>
            </ul>
        </div>
    </div>
    {{/each}}
    {{else}}
    <tr><td align="center" colspan="6">没有找到相关的记录！</td></tr>
    {{/if}}
</script>

<script id="sourceCodeListTemplate1" type="text/html">
    {{if data }}
        {{each data item i}}
            <tr>
                <td>{{item.id}}</td>
                <td>{{item.name}}</td>
                <td>{{item.remark}}</td>
                <td><i class="fa fa-github"></i> {{item.vcsUrl}}</td>
                <td>
                    {{if item.status == 1}}
                    <span class="label label-success label-mini">Active</span>
                    {{else}}
                    <span class="label label-warning label-mini">Closed</span>
                    {{/if}}
                </td>
                <td>
                    {{item.createTime}}
                </td>
                <td>
                    <button class="btn btn-primary btn-sm" onclick="sourceCodeDetail('{{item.id}}')"><i class="fa fa-eye"></i> 详情</button>
                    &nbsp;
                    <button class="btn btn-info btn-sm"><i class="fa fa-pencil"></i> 修改</button>
                    &nbsp;
                    <button class="btn btn-warning btn-sm" onclick="sourceCodeSync('{{item.id}}')"><i class="fa  fa-refresh"></i> 同步</button>
                </td>
            </tr>
        {{/each}}
    {{else}}
        <tr><td align="center" colspan="6">没有找到相关的记录！</td></tr>
    {{/if}}
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    $(function () {
        query();
    });

    function query(){
        Core.get('/api/projects',null, function(data){
            console.log('查询本地仓库列表', data);
            $("#sourceCodeListBody").html(template("sourceCodeListTemplate", data));
        });
    }

    //$('#sourceCodeForm').validate();
    function createProject(){
        var validResult = $("#sourceCodeForm").valid();
        console.log('validateResult', validResult);
        var data = $("#sourceCodeForm").serializeData();
        Core.post('/api/project', data, function(msg){
            console.log(msg);
            if(msg.status == 200){
                toastr.success("创建成功");

                //手动关闭 模态框
                $('#createProjectModal').modal('hide');

                //$("#sourceCodeForm").reset();

                //重置表单
                document.getElementById("sourceCodeForm").reset();


                query();
            } else {
                toastr.error(msg.msg, "提示")
            }
        });
    }

    /**
     * 测试
     */
    function testConnection(){
        var data = $("#sourceCodeForm").serializeData();
        if(data.vcsUrl){
            console.log('serializeData', data)
            //TODO 远程仓库连接测试
            Core.post('/api/vcsTest', data, function(msg){
               console.log(msg);
               if(msg.status == 200){
                   toastr.success(data.vcsUrl + " connection success");
               } else {
                   toastr.error(msg.msg, "提示")
               }
            });

        } else {
            toastr.error("请输入远程代码仓库地址", "提示")
        }
    }

    function sourceCodeDetail(id) {
        if (id && id > 0)
            window.open("/admin/project/detail?id=" + id);

    }

    function sourceCodeSync(id) {
        Core.post("/api/projectSync", {id: id}, function (data) {
            console.log(data);
            if (data.status == 200) {
                if (data.data > 0) {
                    toastr.success("成功同步" + data.data + "个分支", "提示")
                } else {
                    toastr.info("当前已经是最新的", "提示")
                }
            } else {
                toastr.error(data.msg, "提示")
            }
        })
    }
</script>
</body>
</html>
