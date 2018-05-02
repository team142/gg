import {NetworkController} from './NetworkController.js'
import {Web} from '../view/Web.js'
import {BabylonController} from './BabylonController.js'
import {match} from '../model/Match.js'

export class MessageRouter {

    static incoming(event) {

        if (event.data === "0") {
            NetworkController.send("1")
            return
        }

        const obj = JSON.parse(event.data)
        const conversation = obj.conversation

        if (conversation === "S_CHANGE_VIEW") {
            Web.changeView(obj.view)

        } else if (conversation === "S_LIST_OF_GAMES") {
            Web.showListOfGames(obj.games)

        } else if (conversation === "S_SCOREBOARD") {
            BabylonController.handleScoreboard(obj)

        } else if (conversation === "S_SHARE_TAG") {
            match.tag = obj.tag

        } else if (conversation === "S_SHARE_MAP") {
            BabylonController.handleMap(obj)

        } else if (conversation === "S_PLAY_SOUND") {
            BabylonController.handleSound(obj)

        } else if (conversation === "S_PLAYER_LEFT") {
            match.playerLeaves(obj.tag)

        } else if (conversation === "S_SHARE_BULLETS") {
            BabylonController.handleNewBullet(obj)

        } else if (conversation === "S_SHARE_SPRAY") {
            BabylonController.handleNewAnimation(obj)

        } else if (conversation === "S_SHARE_DYNAMIC_THINGS") {
            BabylonController.handleThingsMoving(obj)

        } else if (conversation === "S_SHARE_INTEL") {
            match.setMiniMapOn(obj.on == true)

        } else if (conversation === "S_ORB_N") {
            BabylonController.handleNewOrb(obj)

        } else if (conversation === "S_ORB_D") {
            BabylonController.handleOrbGone(obj)

        } else if (conversation === "S_SHARE_COOLDOWN") {
            BabylonController.handleCooldown(obj)

        } else if (conversation === "S_P_LEVEL") {
            BabylonController.setPowerLevel(obj.key, obj.level)

        } else if (conversation === "S_SHARE_RADAR") {
            BabylonController.shareRadar(obj)

        } else if (conversation === "S_SHARE_HEALTH") {
            BabylonController.handleHealth(obj)

        } else {
            console.log("Don't know what to do with this:")
            console.log(obj)
        }

    }

}

