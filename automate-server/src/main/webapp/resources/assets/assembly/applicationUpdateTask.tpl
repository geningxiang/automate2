<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">任务名称</label>
    <div class="col-sm-10">
        <input type="text" class="form-control" name="name" value="{{name || '应用更新任务'}}" required>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">文件包路径</label>
    <div class="col-sm-10">
        <p class="help-block">${baseDir}代表项目源码文件夹</p>
        <p class="help-block">例如: ${baseDir}/target/xxx.war</p>
        <p class="help-block">请确保先执行构建任务，比如 maven package</p>
        <input class="form-control" name="path" value="{{path}}" required>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">容器</label>
    <div class="col-sm-10">
        <p class="help-block">暂时先填ID吧, low是low了点</p>
        <input class="form-control" name="containerId" value="{{containerId}}" required>
    </div>
</div>