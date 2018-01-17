var postman = {}

postman.incoming = function (event) {
    if (event.data == "0") {
        sio.send("1")
        return
    }

    var obj = JSON.parse(event.data)
    var conversation = obj.conversation
    if (conversation == "S_CHANGE_VIEW") {
        web.changeView(obj.view)

    } else if (conversation == "S_LIST_OF_GAMES") {
        web.showListOfGames(obj.games)

    } else if (conversation == "S_SCOREBOARD") {
        postman.scoreboard(obj)

    } else if (conversation == "S_SHARE_TAG") {
        match.tag = obj.tag

    } else if (conversation == "S_SHARE_MAP") {
        BabylonUtils.createMap(obj.MAP)

    } else if (conversation == "S_PLAY_SOUND") {
        SoundUtils.playSound(obj.FILE)

    } else if (conversation == "S_PLAYER_LEFT") {
        postman.playerLeft(obj)

    } else if (conversation == "S_SHARE_BULLETS") {
        BabylonUtils.createBullet(obj)

    } else if (conversation == "S_SHARE_SPRAY") {
        BabylonUtils.createSpray(obj.tagId, obj.ms)

    } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
        postman.recievedDynamicThings(obj)

    } else {
        console.log("Dont know what to do with this:")
        console.log(obj)
    }
    // alert("Unhandled message" + event.data)

}

postman.scoreboard = function (obj) {
    for (const key of Object.keys(obj.TAGS)) {
        BabylonUtils.createSphereIfNotExists(obj.TAGS[key], key)
    }

    game.scores = []
    for (const key of Object.keys(obj.SCORES)) {
        game.scores.push({
            key: key,
            value: obj.SCORES[key]
        })
    }
    game.scores.sort(function (a, b) {
        return a.value - b.value
    })
    BabylonUtils.displayScores()

}

postman.playerLeft = function (obj) {
    getPlayerByTag(obj.tag).dispose()
    match.playerTanks.delete(obj.tag)

}

postman.recievedDynamicThings = function (obj) {
    for (const t of obj.THINGS) {
        if (t.tag == match.tag) {
            baby.camera.position.x = t.x
            baby.camera.position.y = t.y + 0.25
            baby.camera.position.z = t.z
            baby.camera.rotation.y = t.rotation
        }
        var s = getPlayerByTag(t.tag)
        if (s) {
            s.position.x = t.x
            s.position.y = t.y
            s.position.z = t.z
            s.rotation.y = t.rotation
        }
    }

}