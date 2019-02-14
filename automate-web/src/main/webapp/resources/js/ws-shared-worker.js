// sharedWorker所要用到的js文件，不必打包到项目中，直接放到服务器即可
var data = '';
var index = 0;


var ports = [];
onconnect = function (e) {
    var port = e.ports[0];

    port.index = index++;
    ports.push(port);


    port.onmessage = function (e) {
        port.lastMsgTime = new Date().getTime();
        // port.postMessage("return:" + e.data);
    }
};

setInterval(function(){
    console.log('ports', ports);

    for (var i = 0; i < ports.length; i++) {
        ports[i].postMessage("hello:" + ports[i].index + " | " + ports[i].lastMsgTime);
    }


}, 5000);