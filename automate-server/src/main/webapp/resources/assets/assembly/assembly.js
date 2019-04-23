!function (window, $) {
    var ASSEMBLY_MAP = {
        'com.automate.task.background.assembly.impl.MavenAssemblyStepTask': {
            name: 'Maven任务',
            tpl: '/resources/assets/assembly/mavenTask.tpl'
        },
        'com.automate.task.background.assembly.impl.ExecAssemblyStepTask': {
            name: '自定义Exec任务',
            tpl: '/resources/assets/assembly/execTask.tpl'
        },
        'com.automate.task.background.assembly.impl.PackageExtractAssemblyStepTask': {
            name: '文件包提取任务',
            tpl: '/resources/assets/assembly/packageExtractTask.tpl'
        },
        'com.automate.task.background.assembly.impl.ApplicationUpdateAssemblyStepTask': {
            name: '应用更新任务',
            tpl: '/resources/assets/assembly/applicationUpdateTask.tpl'
        }
    }

    //存数据
    var tempMap = {};

    var showContent = function (taskKey) {
        var data = tempMap[taskKey];
        var task = ASSEMBLY_MAP[data.className];
        $("#taskContent").html(Core.templateTpl(task.tpl, data));
    };


    var checkForm = function () {
        return $("#taskContent").valid();
    }
    /**
     * 添加任务
     * @param clsName
     */
    window.addTask = function (clsName) {
        console.log('添加任务', clsName);

        if (!ASSEMBLY_MAP[clsName]) {
            alert('任务类型错误:' + clsName);
            return;
        }
        if (checkForm()) {
            var item = {};
            item.className = clsName;
            item.key = '' + keyIndex++;
            tempMap[item.key] = item;
            $("#step-ul li.active").removeClass("active");
            $("#step-ul").append('<li class="active" data-task-key="' + item.key + '">' + ASSEMBLY_MAP[item.className].name + '<i class="fa fa-times"></i></li>');
            showContent(item.key);
        }
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
            console.log('save', data);
        }
    };

    var keyIndex = 1;
    window.AssemblyUtil = {
        init: function (jsonStr) {
            var jsonArray = [];
            try {
                jsonArray = JSON.parse(jsonStr.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n"));
                console.log(jsonArray);
            } catch (e) {
                console.warn(e);
            }

            var item;
            for (var i = 0; i < jsonArray.length; i++) {
                item = jsonArray[i];
                if (ASSEMBLY_MAP[item.className]) {
                    item.key = '' + keyIndex++;
                    tempMap[item.key] = item;

                    $("#step-ul").append('<li ' + (i == 0 ? 'class="active"' : '') + 'data-task-key="' + item.key + '">' + ASSEMBLY_MAP[item.className].name + '<i class="fa fa-times"></i></li>');
                    if (i == 0) {
                        showContent(item.key);
                    }
                } else {
                    console.log('className不正确', item);
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

            $("#taskContent").change(function () {
                if ($("#taskContent").valid()) {
                    saveTask();
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
                for (var key in tempMap) {
                    list.push(tempMap[key]);
                }
                data.config = JSON.stringify(list);
                console.log(data);

                $.post('/api/assembly/assemblyLine', data, function (msg) {
                    console.log(msg);
                    if (msg.status == 200) {
                        alert("保存成功");
                        window.opener && window.opener.location.reload();
                        window.close();
                    } else {
                        alert(msg.msg);
                    }
                });
            });
        }
    };
}(window, jQuery);