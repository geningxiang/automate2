!function (window, $) {
    var ASSEMBLY_MAP = {
        'com.automate.task.background.impl.MavenTask': {
            name: 'Maven任务',
            build: function (data) {
                data = data || {};
                return '<div class="form-group">\n' +
                    '                            <label class="col-sm-2 col-sm-2 control-label">任务名称</label>\n' +
                    '                            <div class="col-sm-10">\n' +
                    '                                <input type="text" class="form-control" value="'+(data.name || 'Maven任务')+'">\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 col-sm-2 control-label">Skip Tests</label>\n' +
                    '                            <div class="col-sm-10">\n' +
                    '                                <div class="checkbox"><label><input type="checkbox" value="" ' + (data.testSkip == false ? '' : 'checked') + '> 是否忽略Tests</label></div>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 col-sm-2 control-label">Lifecycle</label>\n' +
                    '                            <div class="col-sm-10">\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="clean" '+(data.lifecycle == 'clean' ? 'checked' : '')+'> clean</label></div>\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="validate" '+(data.lifecycle == 'validate' ? 'checked' : '')+'> validate</label></div>\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="compile" '+(!data.lifecycle || data.lifecycle == 'compile' ? 'checked' : '')+'> compile</label></div>\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="test" '+(data.lifecycle == 'test' ? 'checked' : '')+'> test</label></div>\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="package" '+(data.lifecycle == 'package' ? 'checked' : '')+'> package</label></div>\n' +
                    '                                <div class="radio"><label><input type="radio" name="lifecycle" value="install" '+(data.lifecycle == 'install' ? 'checked' : '')+'> install</label></div>\n' +
                    '                            </div>\n' +
                    '                        </div>';
            }
        },
        'com.automate.task.background.impl.ExecTask': {
            name: '自定义Exec任务',
            build: function (data) {
                return '<div class="form-group">\n' +
                    '                            <label class="col-sm-2 col-sm-2 control-label">任务名称</label>\n' +
                    '                            <div class="col-sm-10">\n' +
                    '                                <input type="text" class="form-control">\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 col-sm-2 control-label">Exec命令</label>\n' +
                    '                            <div class="col-sm-10">\n' +
                    '                                <p class="help-block">多命令请以换行分割, 支持#注释</p>\n' +
                    '                                <textarea type="text" class="form-control" rows="10"></textarea>\n' +
                    '                            </div>\n' +
                    '                        </div>';
            }
        },
    }

    var tempMap = {};
    var showContent = function (taskKey) {
        var data = tempMap[taskKey];

        $("#taskContent").html(ASSEMBLY_MAP[data.className].build(data));
    };

    window.addTask = function(clsName){
        console.log('addTask', clsName);

        if(!ASSEMBLY_MAP[clsName]){
            alert('任务类型错误:' + clsName);
            return;
        }
        var item = {};
        item.className = clsName;
        item.key = '' + keyIndex++;
        tempMap[item.key] = item;
        $("#step-ul li.active").removeClass("active");
        $("#step-ul").append('<li class="active" data-task-key="' + item.key + '">' + ASSEMBLY_MAP[item.className].name + '<i class="fa fa-times"></i></li>');
        showContent(item.key);

    };

    var saveTask = function () {

        var taskKey = $("#step-ul li.active").attr("data-task-key");
        if (taskKey) {
            var data = tempMap[taskKey];
            var serializeArray = $("#taskContent").serializeArray();
            for (var i = 0; i < serializeArray.length; i++) {
                var item = serializeArray[i];
                data[item.name] = item.value;
            }
            tempMap[taskKey] = data;
        }
    };

    var keyIndex = 1;
    window.AssemblyUtil = {
        init: function (jsonStr) {
            var jsonArray = [];
            try{
                jsonArray = JSON.parse(jsonStr);
                console.log(jsonArray);
            }catch (e) {
                console.warn(e);
            }


            var item;
            for (var i = 0; i < jsonArray.length; i++) {
                item = jsonArray[i];
                item.key = '' + keyIndex++;
                tempMap[item.key] = item;

                $("#step-ul").append('<li ' + (i == 0 ? 'class="active"' : '') + 'data-task-key="' + item.key + '">' + ASSEMBLY_MAP[item.className].name + '<i class="fa fa-times"></i></li>');
                if (i == 0) {
                    showContent(item.key);
                }
            }


            $("#step-ul").on("click", "li", function () {
                var taskKey = $(this).attr("data-task-key");
                console.log('onClick', taskKey);

                if (taskKey) {
                    $("#step-ul li.active").removeClass("active");
                    $(this).addClass("active");
                    showContent(taskKey);
                }
            });


            $("#doSubmit").click(function () {
                saveTask();

                var data = {};
                var serializeArray = $("#myForm").serializeArray();
                for (var i = 0; i < serializeArray.length; i++) {
                    var item = serializeArray[i];
                    data[item.name] = item.value;
                }

                var list = [];
                for(var key in tempMap){
                    list.push(tempMap[key]);
                }
                data.config = JSON.stringify(list);
                console.log(data);

                $.post('/api/assembly/assemblyLine', data, function(msg){
                    console.log(msg);
                });
            });
        }
    };
}(window, jQuery);