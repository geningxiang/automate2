"use strict";


!(function (window, $, template) {


    //模板缓存 暂时只用内存缓存  可以考虑用 window.localStorage
    var templateCache = {};

    var core = {
        /**
         * 从当前链接获取参数的值
         * @param param 参数名
         * @returns string
         */
        getParam: function (param) {
            var g = new RegExp("(\\?|#|&)" + param + "=([^&#]*)(&|#|$)");
            var d = location.href.match(g);
            return d ? d[2] || "" : "";
        },

        /**
         * 使用指定的模板地址  解析
         * @param tplUrl
         * @param data
         * @returns string
         */
        templateTpl: function (tplUrl, data) {
            var render = templateCache[tplUrl];
            if (render) {
                return render(data);
            } else {
                var s = '';
                $.ajax({
                    type: "get",
                    url: tplUrl + "?t=" + new Date().getTime(),
                    //同步执行
                    async: false,
                    success: function (msg) {
                        render = template.compile(msg);
                        templateCache[tplUrl] = render;
                        s = render(data);
                    }
                });
                return s;
            }
        },
        dateFormat: function (d, fmt) {
            var date;
            if (!d) {
                return '';
            } else if (typeof (d) == 'number') {
                date = new Date(d);
            } else if (d instanceof Date) {
                date = d;
            } else {
                return '';
            }
            try {
                var o = {
                    "M+": date.getMonth() + 1, //月份
                    "d+": date.getDate(), //日
                    "H+": date.getHours(), //小时
                    "m+": date.getMinutes(), //分
                    "s+": date.getSeconds(), //秒
                    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                    "S": date.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
            } catch (e) {
                console.error(e);
                return '';
            }

        },
        pageFooter: function (data) {
            if (!data) return '';
            var pageTotal = data.totalPages || 1;
            var pageNo = data.number + 1;
            var pageSize = data.size || 20;
            //当前页条数
            var contentSize = data.numberOfElements || 1;
            var total = data.totalElements;
            return Core.templateTpl('/resources/tpl/pagination.tpl', {
                pageNo: pageNo,
                pageTotal: pageTotal,
                pageSize: pageSize,
                contentSize: contentSize,
                total: total
            });
        },
        get: function (url, data, callBack, dataType) {
            $.get(url, data, callBack, dataType || 'json');
        },
        post: function (url, data, callBack, dataType) {
            $.post(url, data, callBack, dataType || 'json');
        },
        ajax: function (settings) {
            if (settings) {
                settings.dataType = settings.dataType || 'json';
                $.ajax(settings);
            }
        }
    };


    //引入默认方法
    template.defaults.imports.parseInt = parseInt;
    template.defaults.imports.Math = Math;
    template.defaults.imports.console = console;
    template.defaults.imports.dateFormat = core.dateFormat;
    template.defaults.imports.pageFooter = core.pageFooter;


    //原生写法， 直接注册到window.Core  这里暂时未考虑 AMD、CMD 规范
    window.Core = core;

    $.fn.extend({
        /**
         * 扩展   serializeArray 方法  直接返回 对象
         */
        'serializeData': function () {
            var data = {};
            console.log(this);

            var list =this.serializeArray();
            for (var i = 0; i < list.length; i++) {
                data[list[i].name] = list[i].value;
            }
            return data;
        }
    });

}(window, window.jQuery, window.template));

