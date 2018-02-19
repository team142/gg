
import { BabylonUtils } from '../view/BabylonUtils.js'
import { BabylonSounds } from '../view/BabylonSounds.js'
import { BabylonAnimations } from '../view/BabylonAnimations.js'
import { Bullet } from '../model/Bullet.js'
import { PowerCooldownBar } from '../model/PowerCooldownBar.js'
import { Orb } from '../model/Orb.js'
import { BabylonModels } from '../view/BabylonModels.js'
export class BabylonController {

    static handleScoreboard(obj) {
        BabylonUI.scoreboard(obj)
    }

    static handleMap(obj) {
        BabylonUtils.createMap(obj)
    }

    static handleSound(obj) {
        BabylonSounds.playSound(obj.FILE)
    }

    static handleNewBullet(obj) {
        Bullet.createAndSave(obj)
    }

    static handleNewAnimation(obj) {
        BabylonAnimations.createSpray(obj.tagId, obj.ms)
    }

    static handleThingsMoving(obj) {
        BabylonModels.recievedDynamicThings(obj)
    }

    static handleNewOrb(obj) {
        const orb = new Orb(obj.name, obj.x, obj.z)
        Orb.save(orb)
    }

    static handleOrbGone(obj) {
        Orb.remove(obj.name)        
    }

    static handleCooldown(obj) {
        let index = +obj.num
        let item = PowerCooldownBar.get(index.toString())
        item.changeRefresh(obj.ticks)
        item.useIt()
    }

}