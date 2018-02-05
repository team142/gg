import { match } from '../model/Match.js'
import { MessageRouter } from '../controller/MessageRouter.js'
import { Web } from '../view/Web.js'

const sio = {}

export class NetworkController {

    static joinServer(url, name) {
        match.username = name
        sio.socket = new WebSocket("ws://" + url + "websocket")
        NetworkController.assignMethods()

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
        sio.socket.onmessage = MessageRouter.incoming
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

    static sendKeyUp(key) {
        NetworkController.send(JSON.stringify(
            {
                conversation: "P_KU",
                key: key
            }
        ))

    }

    static sendKeyDown(key) {
        NetworkController.send(JSON.stringify(
            {
                conversation: "P_KD",
                key: key
            }
        ))

    }

}
