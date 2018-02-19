
import { baby } from '../model/Baby.js'
import { PowerCooldownBar } from '../model/PowerCooldownBar.js'
import { BabylonUtils } from './BabylonUtils.js'
import { match } from '../model/Match.js'
import { passiveIconInfo, powerIconInfo} from '../model/Power.js'
import { game } from '../model/Game.js'
import { TEXTURES_DIR } from './BabylonTextures.js'

/*
    This class is specfically for create / edit Babylon UI components
*/
export class BabylonUI {


    static changeMyHealthBar(health, maxHealth) {
        let potentialWidth = 8 * 80
        let actualWidth = health / maxHealth * potentialWidth
        let di = potentialWidth - actualWidth

        match.healthBar.width = actualWidth + "px"
        match.healthBar.left = 0 - (di / 2)
    }

    static setHealthRectangle(rect1, health, totalHealth) {
        rect1.width = health / totalHealth * 0.2
        if (match.miniMapOn != rect1.isVisible) {
            rect1.isVisible = match.miniMapOn
        }

    }
    static displayScores() {
        baby.textScores.forEach(ro => {
            ro.dispose()
        })
        game.scores.forEach((row, i) => {
            BabylonUI.createRightText(i, row.key, row.value)
        })

    }

    static scoreboard(obj) {

        Object.keys(obj.tags)
            .forEach(key => BabylonUtils.createSphereIfNotExists(obj.tags[key], key))

        game.scores = []

        Object.keys(obj.scores)
            .forEach(key => game.scores.push(
                {
                    key: key,
                    value: obj.scores[key]
                }
            ))
        game.scores.sort((a, b) => a.value - b.value)
        BabylonUI.displayScores()

    }
    static createGui() {
        baby.advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI")
        baby.panelScores = new BABYLON.GUI.StackPanel()
        baby.panelScores.width = "220px"
        baby.panelScores.height = "200px"
        baby.panelScores.fontSize = "14px"
        baby.panelScores.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT
        baby.panelScores.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER
        baby.advancedTexture.addControl(baby.panelScores)

    }

    static createTopPowerBar() {

        let powerBack = new BABYLON.GUI.Rectangle();
        let w = 10 * 80 + 10
        powerBack.width = w + "px"
        powerBack.height = "95px"
        powerBack.cornerRadius = 20
        powerBack.color = "Black"
        powerBack.thickness = 4
        powerBack.background = "Black"
        powerBack.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        powerBack.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP
        baby.advancedTexture.addControl(powerBack)


        passiveIconInfo
            .filter(p => p.usable)
            .forEach(p => {
                BabylonUI.createTopPowerBarItem(p.key, p.ico)
                PowerCooldownBar.save(
                    (p.key).toString(),
                    new PowerCooldownBar(BabylonUI.createPowerBarCooldownTile(p.id - 1, BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP), p.cooldown)
                )
            })

    }


    static createBotPowerBar() {

        let powerBack = new BABYLON.GUI.Rectangle();
        let w = 10 * 80 + 10
        powerBack.width = w + "px"
        powerBack.height = "95px"
        powerBack.cornerRadius = 20
        powerBack.color = "Black"
        powerBack.thickness = 4
        powerBack.background = "Black"
        powerBack.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        powerBack.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM
        baby.advancedTexture.addControl(powerBack)


        powerIconInfo
            .filter(p => p.usable)
            .forEach(p => {
                BabylonUI.createBotPowerBarItem(p.powerNumber - 1, p.ico)
                PowerCooldownBar.save(
                    (p.powerNumber).toString(),
                    new PowerCooldownBar(BabylonUI.createPowerBarCooldownTile(p.powerNumber - 1, BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM), p.cooldown)
                )
            })

    }

    static createPowerBarCooldownTile(n, vAlign) {
        let image = new BABYLON.GUI.Image("cooldownTile" + n, TEXTURES_DIR + "ico-blank.jpg")
        image.height = "75px"
        image.width = "75px"
        image.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        image.verticalAlignment = vAlign

        //Left position
        let x = (75 + 5) * +n //Defaul space for a tile
        x = x - ((75 + 5) * (10 / 2 - 0.5)) //Center in middle
        image.left = x + "px"
        image.top = "-10px"

        baby.advancedTexture.addControl(image)
        return image
    }

    static createBotPowerBarItem(n, fileImage) {

        let image = new BABYLON.GUI.Image("powerBot" + n, fileImage)
        image.height = "75px"
        image.width = "75px"
        image.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        image.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM

        //Left position
        let x = (75 + 5) * +n //Defaul space for a tile
        x = x - ((75 + 5) * (10 / 2 - 0.5)) //Center in middle
        image.left = x + "px"
        image.top = "-10px"
        baby.advancedTexture.addControl(image)

        var text1 = new BABYLON.GUI.TextBlock("textblock" + n)
        text1.text = (n + 1).toString()
        text1.color = "black"
        text1.fontSize = 24

        text1.textHorizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        text1.textVerticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM

        text1.left = (x - 75 / 2 + 7) + "px"
        text1.top = "-10px"
        baby.advancedTexture.addControl(text1)

    }

    static createTopPowerBarItem(n, fileImage) {

        let image = new BABYLON.GUI.Image("powerBot" + n, fileImage)
        image.height = "75px"
        image.width = "75px"
        image.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        image.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP

        //Left position
        let x = (75 + 5) * +n //Defaul space for a tile
        x = x - ((75 + 5) * (10 / 2 - 0.5)) //Center in middle
        image.left = x + "px"
        image.top = "-10px"
        baby.advancedTexture.addControl(image)

        var text1 = new BABYLON.GUI.TextBlock("textblock" + n)
        text1.text = n
        text1.color = "black"
        text1.fontSize = 24

        text1.textHorizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        text1.textVerticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP

        text1.left = (x - 75 / 2 + 7) + "px"
        text1.top = "-10px"
        baby.advancedTexture.addControl(text1)

    }

    static createOwnHealthBar() {
        let w = 8 * 80

        let healthBarRed = new BABYLON.GUI.Rectangle();
        healthBarRed.width = w + "px"
        healthBarRed.height = "10px"
        healthBarRed.top = "-95px"
        healthBarRed.cornerRadius = 20
        healthBarRed.color = "Red"
        healthBarRed.thickness = 1
        healthBarRed.background = "Red"
        healthBarRed.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        healthBarRed.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM
        baby.advancedTexture.addControl(healthBarRed)

        let healthBarGreen = new BABYLON.GUI.Rectangle();
        healthBarGreen.width = (w) + "px"
        healthBarGreen.height = "10px"
        healthBarGreen.top = "-95px"
        healthBarGreen.cornerRadius = 20
        healthBarGreen.color = "Green"
        healthBarGreen.thickness = 1
        healthBarGreen.background = "Green"
        healthBarGreen.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_CENTER
        healthBarGreen.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM
        baby.advancedTexture.addControl(healthBarGreen)

        match.healthBar = healthBarGreen

    }

    static createRightText(num, name, score) {
        const current = BABYLON.GUI.Button.CreateSimpleButton("but" + BabylonUtils.getCounter(), name + ": " + score)
        current.width = 1
        current.height = "50px"
        current.color = "white"
        current.background = "black"
        baby.panelScores.addControl(current)
        baby.textScores.push(current)

    }

}