import { match } from '../model/Match.js'
import { BabylonSounds } from './BabylonSounds.js'
import { NetworkController } from '../controller/NetworkController.js'
import { game } from '../model/Game.js'
import { Bullet, bullets } from '../model/Bullet.js'
import { BabylonModels } from './BabylonModels.js'
import { BabylonTextures } from './BabylonTextures.js'
import { baby } from '../model/Baby.js'
import { PowerCooldownBar } from '../model/PowerCooldownBar.js'
import { powerIconInfo, passiveIconInfo } from '../model/Power.js'
import { GameMap } from '../model/GameMap.js'

export class BabylonUtils {

    static getCounter() {
        baby.counter++
        return baby.counter

    }

    static setup3D() {
        baby.canvas = document.getElementById("VIEW_CANVAS")
        baby.engine = new BABYLON.Engine(baby.canvas, true)
        BabylonUtils.createScene()
        baby.engine.runRenderLoop(() => {
            baby.scene.render()
        })
        window.addEventListener("resize", () => {
            baby.engine.resize()
        })
        window.addEventListener("keyup", (data) => {
            NetworkController.sendKeyUp(data.key)
        })
        window.addEventListener("keydown", (data) => {
            NetworkController.sendKeyDown(data.key)
        })

        BabylonModels.loadBaseFlatTile()
        BabylonModels.loadBaseMountainTile()
        BabylonModels.createSkyBox()

        BabylonSounds.loadSounds()
        BabylonModels.createBaseBullet()
        BabylonModels.createBaseRandomOrb()

        BabylonUtils.createTopPowerBar()
        BabylonUtils.createBotPowerBar()
        BabylonUtils.createOwnHealthBar()

    }

    static createScene() {
        baby.scene = new BABYLON.Scene(baby.engine)
        baby.scene.name = "scene"
        baby.camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), baby.scene)
        baby.camera.setTarget(BABYLON.Vector3.Zero())
        baby.camera.position.y = 0.75
        const light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), baby.scene)
        light.intensity = 0.9
        BabylonTextures.createMaterials()
        BabylonUtils.createGui()

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
                        BabylonUtils.createTopPowerBarItem(p.key, p.ico)
                        PowerCooldownBar.save(
                            (p.key).toString(),
                            new PowerCooldownBar(BabylonUtils.createPowerBarCooldownTile(p.powerNumber - 1, BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP), p.cooldown)
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
                BabylonUtils.createBotPowerBarItem(p.powerNumber - 1, p.ico)
                PowerCooldownBar.save(
                    (p.powerNumber).toString(),
                    new PowerCooldownBar(BabylonUtils.createPowerBarCooldownTile(p.powerNumber - 1, BABYLON.GUI.Control.VERTICAL_ALIGNMENT_BOTTOM), p.cooldown)
                )
            })

    }

    static createPowerBarCooldownTile(n, vAlign) {
        let image = new BABYLON.GUI.Image("cooldownTile" + n, "textures/ico-blank.jpg")
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

    static changeMyHealthBar(health, maxHealth) {
        let potentialWidth = 8 * 80
        let actualWidth = health / maxHealth * potentialWidth
        let di = potentialWidth - actualWidth

        match.healthBar.width = actualWidth + "px"
        match.healthBar.left = 0 - (di / 2)
    }

    static displayScores() {
        baby.textScores.forEach(ro => {
            ro.dispose()
        })
        game.scores.forEach((row, i) => {
            BabylonUtils.createRightText(i, row.key, row.value)
        })

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

    static setHealthRectangle(rect1, health, totalHealth) {
        rect1.width = health / totalHealth * 0.2
        if (match.miniMapOn != rect1.isVisible) {
            rect1.isVisible = match.miniMapOn
        }

    }

    static createSphereIfNotExists(tagId, labelText) {
        if (tagId) {

            //Ignore players I know of
            const result = match.getPlayerByTag(tagId)
            if (result) {
                return
            }

            const name = "player" + match.gameInstance + tagId

            ////////////////////////////////////////////////////////////////
            ////////////    *** Comes from Tank model project *** //////////
            ////////////////////////////////////////////////////////////////

            let box
            if (match.playerTanks.size > 0) {
                box = match.playerTanks.entries().next().value[1].clone(name)
            } else {
                box = BABYLON.MeshBuilder.CreateBox("box" + name, { height: 0.3, width: 0.6, depth: 0.3 }, baby.scene)
                box.position.y = 0.0
                box.material = baby.matGrey

                const boxBarrel = BABYLON.MeshBuilder.CreateBox("boxBarrel" + name, { height: 0.05, width: 0.6, depth: 0.05 }, baby.scene)
                boxBarrel.position.y = 0.1
                boxBarrel.position.x = 0.3
                boxBarrel.material = baby.matBlack

                const boxLeftWing = BABYLON.MeshBuilder.CreateBox("boxLeftWing" + name, { height: 0.1, width: 0.65, depth: 0.05 }, baby.scene)
                boxLeftWing.position.y = -0.11
                boxLeftWing.position.z = 0.15
                boxLeftWing.material = baby.matWing


                var boxRightWing = BABYLON.MeshBuilder.CreateBox("boxRightWing" + name, { height: 0.1, width: 0.65, depth: 0.05 }, baby.scene)
                boxRightWing.position.y = -0.11
                boxRightWing.position.z = -0.15
                boxRightWing.material = baby.matWing

                box.addChild(boxBarrel)
                box.addChild(boxLeftWing)
                box.addChild(boxRightWing)

            }


            ////////////////////////////////////////////////////////////////////////////////////

            const item = box
            match.playerTanks.set(tagId, item)

            const rectText = new BABYLON.GUI.Rectangle()
            rectText.width = 0.2
            rectText.height = "100px"
            rectText.cornerRadius = 20
            rectText.thickness = 0
            baby.advancedTexture.addControl(rectText)
            match.playerRectangles.set(tagId, rectText)

            const label = new BABYLON.GUI.TextBlock()
            label.text = labelText
            match.playerLabels.set(tagId, label)

            rectText.addControl(label)
            rectText.linkWithMesh(item)
            rectText.linkOffsetY = -50


            const rect1 = new BABYLON.GUI.Rectangle()
            rect1.width = 0.2
            rect1.height = "25px"
            rect1.cornerRadius = 20
            rect1.color = "black"
            rect1.thickness = 3
            rect1.background = "green"
            baby.advancedTexture.addControl(rect1)
            match.playerHealthBars.set(tagId, rect1)

            rect1.linkWithMesh(box)
            rect1.linkOffsetY = -80

        }

    }

    static createMap(obj) {
        GameMap.create(obj)
        obj.map.forEach(t => BabylonUtils.createMapTile(t.point.x, t.point.z, t.skin, t.model))
    }

    static createBullet(obj) {
        Bullet.createAndSave(obj.BULLET, baby.baseBullet.clone("bullet" + BabylonUtils.getCounter()))

    }

    static createMapTile(x, z, skin, model) {
        let plane
        if (model == "FLAT_TILE") {
            plane = baby.baseTile.clone(("plane" + x) + z)
        } else if (model == "ROCK_TILE") {
            plane = baby.mountainTile.clone(("plane" + x) + z)
        }
        plane.position.z = (z * 1)
        plane.position.x = (x * 1)
        plane.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            plane.material = material
        }
        plane.visibility = true

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

}
