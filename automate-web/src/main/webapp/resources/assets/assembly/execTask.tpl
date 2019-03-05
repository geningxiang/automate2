<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">任务名称</label>
    <div class="col-sm-10">
        <input type="text" class="form-control" name="name" value="{{name || '自定义脚本'}}" required>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">Exec命令</label>
    <div class="col-sm-10">
        <p class="help-block">多命令请以换行分割, 支持#注释</p>
        <textarea type="text" class="form-control" name="scripts" rows="10" style="word-wrap:normal;">{{scripts}}</textarea>
    </div>
</div>
