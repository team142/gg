import { ServerIO } from '../controller/ServerIO.js'
import { BabylonUtils } from './BabylonUtils.js'

export class Web {

    static showStartScreen() {
        Web.toggleElement("VIEW_CANVAS", false)
        Web.toggleElement("VIEW_GAMES", false)

    }

    static buttonJoinServer() {
        document.getElementById("btnJoinServer").disabled = true
        const url = document.getElementById("selectServer").value
        const name = document.getElementById("inputName").value
        if (name) {
            document.getElementById("btnJoinServer").enabled = false
            ServerIO.joinServer(url, name)
            Web.storeName()
        } else {
            swal({
                type: 'error',
                title: 'Oops...',
                text: 'Please choose a nickname',
                footer: '<a href="https://dictionary.cambridge.org/dictionary/english/nickname">What is a nickname?</a>',
            })
            document.getElementById("btnJoinServer").disabled = false
        }

    }

    static showListOfGames(games) {
        const body = {
            conversation: "P_REQUEST_JOIN_GAME",
            id: games[0].id
        }
        const json = JSON.stringify(body)
        ServerIO.send(json)

    }

    static changeView(view) {
        Web.toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
        Web.toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
        Web.toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
        if (view == "VIEW_CANVAS") {
            BabylonUtils.setup3D()
        }

    }

    static toggleElement(id, toggle) {
        if (toggle) {
            document.getElementById(id).style.display = "block"
            document.getElementById(id).style.visibility = "visible"
        } else {
            document.getElementById(id).style.visibility = "hidden"
            document.getElementById(id).style.display = "none"
        }

    }

    static retrievePreviousName() {
        let name = localStorage.getItem("GG_NAME")
        if (name) {
            document.getElementById("inputName").value = name
        }
    }

    static storeName() {
        let name = document.getElementById("inputName").value
        localStorage.setItem("GG_NAME", name)
    }

    static chooseServer() {
        const localHostname = window.location.hostname
        const port = window.location.port
        let s
        if (port == 80) {
            s = localHostname + "/"
        } else {
            s = localHostname + ":" + port + "/"
        }
        document.querySelector('#selectServer [value="' + s + '"]').selected = true

    }

    static assignJoinButton() {
        document.getElementById("btnJoinServer").addEventListener("click", () => { Web.buttonJoinServer() })
    }

}


