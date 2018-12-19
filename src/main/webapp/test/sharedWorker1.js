
var myPorts = [];
onconnect = function(e) {

    var port = e.ports[0];
    myPorts.push(port);
    port.onmessage = function(d) {
        for (var i = 0; i < myPorts.length; i++) {
            myPorts[i].postMessage("return:" + d.data);
        }

    }

    // port.addEventListener('message', function(e) {
    //     var workerResult = 'Result: ' + (e.data[0] * e.data[1]);
    //     port.postMessage(workerResult);
    // });
    //
    // port.start(); // Required when using addEventListener. Otherwise called implicitly by onmessage setter.
}

