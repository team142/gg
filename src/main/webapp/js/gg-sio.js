
var sio = {}
var socket

sio.joinServer = function (url, name) {
    match.username = name
    socket = new WebSocket("ws://" + url + "websocket")
    sio.assignMethods()

}

sio.sendKeyUp = function (key) {
    sio.send(JSON.stringify(
        {
            conversation: "P_KU",
            key: key
        }
    ))

}

sio.sendKeyDown = function (key) {
    sio.send(JSON.stringify(
        {
            conversation: "P_KD",
            key: key
        }
    ))

}

sio.assignMethods = function () {
    socket.onopen = function (event) {
        socket.send(JSON.stringify(
            {
                conversation: "P_REQUEST_JOIN_SERVER",
                name: match.username
            }
        ))
    }
    socket.onmessage = postman.incoming

}

sio.send = function (msg) {
    socket.send(msg)

}

