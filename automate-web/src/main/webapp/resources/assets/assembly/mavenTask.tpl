<div class="form-group">
<label class="col-sm-2 col-sm-2 control-label">任务名称</label>
<div class="col-sm-10">
    <input class="form-control" name="name" value="{{name || 'maven任务'}}" required>
</div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">Skip Tests</label>
    <div class="col-sm-10">
        <div class="checkbox"><label><input type="checkbox" name="testSkip" value="1" {{testSkip == undefined || testSkip ? 'checked' : ''}}> 是否忽略Tests</label></div>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label">Lifecycle</label>
    <div class="col-sm-10">
        <div class="radio"><label><input type="radio" name="shortcut" value="clean" {{shortcut == 'clean' ? 'checked' : ''}} > clean</label></div>
        <div class="radio"><label><input type="radio" name="shortcut" value="validate" {{shortcut == 'validate' ? 'checked' : ''}}> validate</label></div>
        <div class="radio"><label><input type="radio" name="shortcut" value="compile" {{shortcut == 'compile' ? 'checked' : ''}} > compile</label></div>
        <div class="radio"><label><input type="radio" name="shortcut" value="test" {{shortcut == 'test' ? 'checked' : ''}} > test</label></div>
        <div class="radio"><label><input type="radio" name="shortcut" value="package" {{shortcut == 'package' ? 'checked' : ''}} > package</label></div>
        <div class="radio"><label><input type="radio" name="shortcut" value="install" {{shortcut == 'install' ? 'checked' : ''}} > install</label></div>
    </div>
</div>