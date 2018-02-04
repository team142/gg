
export class Postman {

    static incoming(event) {

        if (event.data == "0") {
            ServerIO.send("1")
            return
        }

        const obj = JSON.parse(event.data)
        const conversation = obj.conversation

        if (conversation == "S_CHANGE_VIEW") {
            Web.changeView(obj.view)

        } else if (conversation == "S_LIST_OF_GAMES") {
            Web.showListOfGames(obj.games)

        } else if (conversation == "S_SCOREBOARD") {
            Postman.scoreboard(obj)

        } else if (conversation == "S_SHARE_TAG") {
            match.tag = obj.tag

        } else if (conversation == "S_SHARE_MAP") {
            BabylonUtils.createMap(obj.MAP)

        } else if (conversation == "S_PLAY_SOUND") {
            SoundUtils.playSound(obj.FILE)

        } else if (conversation == "S_PLAYER_LEFT") {
            Postman.playerLeft(obj)

        } else if (conversation == "S_SHARE_BULLETS") {
            BabylonUtils.createBullet(obj)

        } else if (conversation == "S_SHARE_SPRAY") {
            BabylonUtils.createSpray(obj.tagId, obj.ms)

        } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
            Postman.recievedDynamicThings(obj)

        } else {
            console.log("Dont know what to do with this:")
            console.log(obj)
        }

    }

    static scoreboard(obj) {
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
        game.scores.sort((a, b) => {
            return a.value - b.value
        })
        BabylonUtils.displayScores()

    }

    static playerLeft(obj) {
        Match.playerLeaves(obj.tag)
    }

    static recievedDynamicThings(obj) {
        for (const t of obj.things) {
            if (t.tag == match.tag) {
                baby.camera.position.x = t.x
                baby.camera.position.y = t.y + 0.25
                baby.camera.position.z = t.z
                baby.camera.rotation.y = t.rotation

                //Move the healthbar
                BabylonUtils.changeMyHealthBar(t.health, t.maxHealth)
            }
            const s = Match.getPlayerByTag(t.tag)
            if (s) {
                s.position.x = t.x
                s.position.y = t.y
                s.position.z = t.z
                s.rotation.y = t.rotation - 1.57
            }
            const rect1 = Match.getHealthBarByTag(t.tag)
            if (rect1) {
                BabylonUtils.setHealthRectangle(rect1, t.health, t.maxHealth)
            }


        }

    }

}

