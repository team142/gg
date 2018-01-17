
const sio = {}

class ServerIO {

    static joinServer(url, name) {
        match.username = name
        sio.socket = new WebSocket("ws://" + url + "websocket")
        ServerIO.assignMethods()

    }

    static sendKeyUp(key) {
        ServerIO.send(JSON.stringify(
            {
                conversation: "P_KU",
                key: key
            }
        ))

    }

    static sendKeyDown(key) {
        ServerIO.send(JSON.stringify(
            {
                conversation: "P_KD",
                key: key
            }
        ))

    }

    static assignMethods() {
        sio.socket.onopen = function (event) {
            sio.socket.send(JSON.stringify(
                {
                    conversation: "P_REQUEST_JOIN_SERVER",
                    name: match.username
                }
            ))
        }
        sio.socket.onmessage = Postman.incoming

    }

    static send(msg) {
        sio.socket.send(msg)

    }

}