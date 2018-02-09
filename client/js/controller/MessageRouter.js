import { NetworkController } from './NetworkController.js'
import { Web } from '../view/Web.js'
import { BabylonView } from '../view/BabylonView.js'
import { match } from '../model/Match.js'
import { BabylonUtils } from '../view/BabylonUtils.js'
import { BabylonSounds } from '../view/BabylonSounds.js'
import { BabylonAnimations } from '../view/BabylonAnimations.js'
import { Bullet } from '../model/Bullet.js'
import { PowerCooldownBar } from '../model/PowerCooldownBar.js'

export class MessageRouter {

    static incoming(event) {

        if (event.data == "0") {
            NetworkController.send("1")
            return
        }

        const obj = JSON.parse(event.data)
        const conversation = obj.conversation

        if (conversation == "S_CHANGE_VIEW") {
            Web.changeView(obj.view)

        } else if (conversation == "S_LIST_OF_GAMES") {
            Web.showListOfGames(obj.games)

        } else if (conversation == "S_SCOREBOARD") {
            BabylonView.scoreboard(obj)

        } else if (conversation == "S_SHARE_TAG") {
            match.tag = obj.tag

        } else if (conversation == "S_SHARE_MAP") {
            BabylonUtils.createMap(obj.MAP)

        } else if (conversation == "S_PLAY_SOUND") {
            BabylonSounds.playSound(obj.FILE)

        } else if (conversation == "S_PLAYER_LEFT") {
            match.playerLeaves(obj.tag)

        } else if (conversation == "S_SHARE_BULLETS") {
            Bullet.createAndSave(obj)

        } else if (conversation == "S_SHARE_SPRAY") {
            BabylonAnimations.createSpray(obj.tagId, obj.ms)

        } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
            BabylonView.recievedDynamicThings(obj)

        } else if (conversation == "S_SHARE_INTEL") {
            match.setMiniMapOn(obj.on == true)

        } else if (conversation == "S_SHARE_COOLDOWN") {
            let item = PowerCooldownBar.get(obj.num)
            item.changeRefresh(obj.ticks)
            item.useIt()

        } else {
            console.log("Dont know what to do with this:")
            console.log(obj)
        }

    }

}

