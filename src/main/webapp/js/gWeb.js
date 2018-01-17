
class Web {

    static buttonJoinServer() {
        document.getElementById("btnJoinServer").disabled = true
        const url = document.getElementById("selectServer").value
        const name = document.getElementById("inputName").value
        if (name) {
            document.getElementById("btnJoinServer").enabled = false
            sio.joinServer(url, name)
        }

    }

    static showListOfGames(games) {
        const body = {
            conversation: "P_REQUEST_JOIN_GAME",
            id: games[0].id
        }
        const json = JSON.stringify(body)
        sio.send(json)

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

}