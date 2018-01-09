
var sio = {

}

sio.joinServer = function (url, name) {
    match.username = name
    sio.socket = new WebSocket("ws://" + url + "websocket")
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
    sio.socket.onopen = function (event) {
        sio.socket.send(JSON.stringify(
            {
                conversation: "P_REQUEST_JOIN_SERVER",
                name: match.username
            }
        ))
    }
    sio.socket.onmessage = postman.incoming

}

sio.send = function (msg) {
    sio.socket.send(msg)

}

