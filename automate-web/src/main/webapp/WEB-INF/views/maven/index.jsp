<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <jsp:include page="../common/head.jsp"/>
    <style>
        #versionListModelContent p{
            margin: 0;
        }
        .lastVersion p{
            color: red;
        }
    </style>
</head>
<body>

<section class="wrapper site-min-height">
    <!-- page start-->
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    Maven私有库
                </header>
                <div class="panel-body">
                    <div class="adv-table">
                        <div id="artifactListContent" class="dataTables_wrapper form-inline" role="grid">

                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <!-- page end-->

    <!-- vertical center large Modal  start -->
    <div class="modal fade modal-dialog-center" id="versionListModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content-wrap">
                <div class="modal-content" id="versionListModelContent">

                </div>
            </div>
        </div>
    </div>
    <!-- vertical center large Modal end -->
</section>
<script id="artifactListTemplate" type="text/html">
    <table class="table table-advance table-hover">
        <thead>
        <tr>
            <th>groupId » artifactId</th>
            <th>最后修改时间</th>
        </tr>
        </thead>
        <tbody>
        {{each data item i}}
        <tr>
            <td><a href="javascript: showVersionList('{{item.groupId}}', '{{item.artifactId}}', '{{item.lastModified}}');">{{item.groupId}} »
                {{item.artifactId}}</a></td>
            <td>{{item.lastModifiedStr}}</td>
        </tr>

        {{/each}}
        </tbody>
    </table>
</script>
<script id="versionListModelContentTemplate" type="text/html">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">{{groupId}} » {{artifactId}}</h4>
    </div>
    <div class="modal-body">
        <table class="table table-advance table-hover">
            <thead>
                <tr>
                    <th>版本</th>
                    <th>最后修改时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            {{each versionList item i}}
            <tr class="{{ item.lastModified >= lastModified ? 'lastVersion' : ''}}">
                <td><p>{{item.version}}</p></td>
                <td><p>{{item.lastModifiedStr}}</p></td>
                <td>
                    <a class="btn btn-primary btn-sm" href="/maven/{{groupId.replace('.','/')}}/{{artifactId}}/{{item.version}}/{{artifactId}}-{{item.version}}.jar" target="_blank"><i class="fa  fa-download"></i> 下载</a>
                    &nbsp;&nbsp;
                    <a class="btn btn-danger btn-sm"><i class="fa  fa-minus-square"></i> 删除</a>
                </td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </div>
</script>
<jsp:include page="../common/common_js.jsp"></jsp:include>
<script>

    /**
     * 存列表
     * @type {Array}
     */
    var data = [];
    /**
     * 存版本信息
     * @type {{'groupId/artifactId':[版本数组]}}
     */
    var groupArtifactVersionMap = {};


    var artifactCompare = function (a, b) {
        if (a.lastModified > b.lastModified) {
            return -1;
        } else if (a.lastModified == b.lastModified) {
            return 0;
        } else {
            return 1;
        }
    };

    var versionCompare = function (a, b) {
        var aa = a.version.split(".");
        var bb = b.version.split(".");
        if (aa.length == bb.length) {
            for (var i = 0; i < aa.length; i++) {
                if (bb[i] == aa[i]) {
                    continue;
                } else if (bb[i] > aa[i]) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        } else {
            return bb.length - aa.length;
        }
    }

    function query() {
        Core.get('/api/mvn/repository', {}, function (msg) {
            // console.log(msg);
            for (var groupId in msg.data) {
                var artifactMap = msg.data[groupId];
                if (artifactMap) {
                    for (var artifactId in artifactMap) {
                        var versionMap = artifactMap[artifactId];
                        var item = {
                            groupId: groupId,
                            artifactId: artifactId,
                            lastModified: 0
                        };

                        var versionList = [];
                        for (var version in versionMap) {
                            var lastModified = versionMap[version];
                            item.lastModified = Math.max(item.lastModified, lastModified);

                            versionList.push({
                                version: version,
                                lastModified: lastModified,
                                lastModifiedStr: Core.dateFormat(lastModified, "yyyy-MM-dd HH:mm:ss")
                            });
                        }
                        //使用时再排序， 降低计算损耗
                        // versionList = versionList.sort(versionCompare);
                        // console.log(groupId + "/" + artifactId, versionList);
                        groupArtifactVersionMap[groupId + '/' + artifactId] = versionList;

                        item.lastModifiedStr = Core.dateFormat(item.lastModified, "yyyy-MM-dd HH:mm:ss");
                        data.push(item);
                    }
                }
            }
            data = data.sort(artifactCompare);
            showList();
        });
    }

    var showList = function () {
        $("#artifactListContent").html(template('artifactListTemplate', {data: data}));
    };


    var showVersionList = function (groupId, artifactId, lastModified) {
        var versionList = groupArtifactVersionMap[groupId + '/' + artifactId];
        console.log('versionList', versionList);
        if (versionList) {
            versionList = versionList.sort(versionCompare);

            $("#versionListModelContent").html(template('versionListModelContentTemplate', {
                groupId: groupId,
                artifactId: artifactId,
                lastModified: lastModified,
                versionList: versionList
            }));

            $('#versionListModal').modal('show');
        }
    }

    $(function () {
        query();
    });
</script>
</body>
</html>
