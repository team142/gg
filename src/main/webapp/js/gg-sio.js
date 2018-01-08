var sio = {}

var socket


sio.joinServer = function (url, name) {
    username = name
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

sio.postBox = function (event) {


    var obj = JSON.parse(event.data)
    var conversation = obj.conversation
    if (conversation == "S_CHANGE_VIEW") {
        web.changeView(obj.view)
    } else if (conversation == "S_LIST_OF_GAMES") {
        web.showListOfGames(obj.games)
    } else if (conversation == "S_SCOREBOARD") {
        obj.TAGS.forEach(t => {
            if (getPlayerByTag(t)) {
                baby.createSphereIfNotExists(t)
            }
        })
    } else if (conversation == "S_SHARE_TAG") {
        tag = obj.tag

    } else if (conversation == "S_SHARE_MAP") {
        baby.createMap(obj.MAP)

    } else if (conversation == "S_PLAY_SOUND") {
        gSound.playSound(obj.FILE)

    } else if (conversation == "S_PLAYER_LEFT") {

        var tagToRemove = obj.tag
        var l = playerTanks.length
        var indexToRemove = -1
        for (var i = 0; i < l; i++) {
            var key = playerTanks[i].key
            if (key == tagToRemove) {
                indexToRemove = i
                playerTanks[i].value.dispose()
            }
        }
        if (indexToRemove > -1) {
            playerTanks.splice(indexToRemove, 1)
        }


    } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
        var l = obj.THINGS.length
        for (var i = 0; i < l; i++) {
            if (obj.THINGS[i].tag == tag) {
                // if (camera) {
                camera.position.x = obj.THINGS[i].x
                camera.position.y = obj.THINGS[i].y + 0.25
                camera.position.z = obj.THINGS[i].z
                camera.rotation.y = obj.THINGS[i].rotation
                // }
            }
            var s = getPlayerByTag(obj.THINGS[i].tag)
            if (s) {
                s.position.x = obj.THINGS[i].x
                s.position.y = obj.THINGS[i].y
                s.position.z = obj.THINGS[i].z
                s.rotation.y = obj.THINGS[i].rotation

            }
        }

    }
    // alert("Unhandled message" + event.data)
}


sio.assignMethods = function () {
    socket.onopen = function (event) {
        var body = {
            conversation: "P_REQUEST_JOIN_SERVER",
            name: username
        }
        var json = JSON.stringify(body)
        socket.send(json)
    }

    socket.onmessage = sio.postBox;

    // socket.onmessage = function (event) {
    // }
}

sio.send = function (msg) {
    socket.send(msg)
}

