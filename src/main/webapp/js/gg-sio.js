var sio = {}
var socket


sio.joinServer = function (url, name) {
    match.username = name
    socket = new WebSocket("ws://" + url + "websocket")
    sio.assignMethods()

}

sio.sendKeyUp = function (key) {
    var message = {
        conversation: "P_KU",
        key: key
    }
    sio.send(JSON.stringify(message))

}
sio.sendKeyDown = function (key) {
    var message = {
        conversation: "P_KD",
        key: key
    }
    sio.send(JSON.stringify(message))

}

sio.assignMethods = function () {
    socket.onopen = function (event) {
        var body = {
            conversation: "P_REQUEST_JOIN_SERVER",
            name: match.username
        }
        var json = JSON.stringify(body)
        socket.send(json)
    }
    socket.onmessage = postman.incoming

}

sio.send = function (msg) {
    socket.send(msg)

}

