import { match } from '../model/Match.js'
import { Postman } from '../controller/Postman.js'
import { Web } from '../view/Web.js'

const sio = {}

export class ServerIO {

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
        sio.socket.onopen = (event) => {
            sio.socket.send(JSON.stringify(
                {
                    conversation: "P_REQUEST_JOIN_SERVER",
                    name: match.username
                }
            ))
        }
        sio.socket.onmessage = Postman.incoming
        sio.socket.onerror = (error) => {
            swal({
                type: 'error',
                title: 'Oops...',
                text: 'Failed to join server',
                footer: 'Did you pick a valid server address?',
            })

            Web.enabledJoinButton()
        }

    }

    static send(msg) {
        sio.socket.send(msg)

    }

}
