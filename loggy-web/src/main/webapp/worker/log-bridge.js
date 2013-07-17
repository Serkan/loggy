var queue = [];
self.addEventListener('message', function (e) {
    var data = e.data;
    switch (data.cmd) {
        case 'start':
            var request = new WebSocket('ws://127.0.0.1:8080/loggy/resource/?X-Atmosphere-tracking-id=0&X-Atmosphere-Framework=1.1.0&X-Atmosphere-Transport=websocket&X-Cache-Date=0&Content-Type=application/json&X-atmo-protocol=true');
            // atmosphere connection establishment
            request.onopen = function () {
                self.postMessage('web socket connection opened');
            }
            request.onmessage = function (msg) {
                queue.push(msg.data);
                self.postMessage(msg.data);
            }
            setInterval(function () {
                if(queue.length != 0){

                }
            }, 2000);
            break;
        case 'stop':
            self.postMessage('WORKER STOPPED');
            self.close(); // Terminates the worker.
            break;
        default:
            self.postMessage('Unknown command');
    }
    ;
}, false);