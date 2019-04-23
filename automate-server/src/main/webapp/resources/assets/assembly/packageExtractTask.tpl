<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">任务名称</label>
    <div class="col-sm-10">
        <input type="text" class="form-control" name="name" value="{{name || '文件包提取'}}" required>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">文件包路径</label>
    <div class="col-sm-10">
        <p class="help-block">${baseDir}代表项目源码文件夹</p>
        <p class="help-block">例如: ${baseDir}/target/xxx.war</p>
        <input class="form-control" name="path" value="{{path}}" required>
    </div>
</div>
