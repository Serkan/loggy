importScripts("../js/jquery.atmosphere.js");
/**
 * Opens a atmosphere connection which may be a websocket, comet or long-polling
 * decided by atmosphere framework autamaticly.
 *
 * @param trackerIPportPair trackerIP:port string
 */
var request = { url: 'http://' + 'localhost:8080' + '/loggy/resource/',
    contentType: "application/json",
    logLevel: 'debug',
    enableProtocol: true,
    transport: 'websocket'
};
// atmosphere connection establishment
request.onOpen = function (response) {
    console.log('Connection established with ' + response);
}

// every log line pushed from server drops to onMessage method, response.responseBody
// contains json object identified by following example
// {"domainName":"test_domain","name":"test_serv01","listenPort":"7003","listenAddress":"application01","logLine":<Server> <Jms> ....... <>}
request.onMessage = function (response) {
//    var obj = JSON.parse(response.responseBody);
//    console.log(obj);
    console.log(response);
}
// after request preparement, opens a socket to given tracker node
var socket = jQuery.atmosphere;
socket.subscribe(request);


