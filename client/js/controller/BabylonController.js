
import { BabylonUtils } from '../view/BabylonUtils.js'
import { BabylonSounds } from '../view/BabylonSounds.js'
import { BabylonAnimations } from '../view/BabylonAnimations.js'
import { Bullet } from '../model/Bullet.js'
import { PowerCooldownBar } from '../model/PowerCooldownBar.js'
import { Orb } from '../model/Orb.js'
import { BabylonModels } from '../view/BabylonModels.js'
import { BabylonUI } from '../view/BabylonUI.js'
import { powerIconInfo } from '../model/Power.js'

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
        let item = PowerCooldownBar.get(obj.key)
        item.changeRefresh(obj.ticks)
        item.useIt()
    }

    static setPowerLevel(powerId, level) {
        const p = powerIconInfo
            .find(power => power.key == powerId)
        if (p) {
            p.level = level
            if (level == 1) {
                BabylonUI.createBotPowerBarItem(p.powerNumber - 1, p.ico)
                PowerCooldownBar.set(
                    (p.powerNumber).toString(),
                    new PowerCooldownBar(BabylonUI.createPowerBarCooldownTile(p.powerNumber - 1, BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM), p.cooldown)
                )

            }
        }
    }
    
    static shareRadar(obj) {
        BabylonUI.createRadar(obj)
        setTimeout(BabylonController.stopRadar, obj.timeout)
    }

    static stopRadar() {
        BabylonUI.stopRadar()
    }
}