import { match } from '../model/Match.js'
import { BabylonSounds } from './BabylonSounds.js'
import { NetworkController } from '../controller/NetworkController.js'
import { Bullet } from '../model/Bullet.js'
import { BabylonModels } from './BabylonModels.js'
import { BabylonTextures } from './BabylonTextures.js'
import { baby } from '../model/Baby.js'
import { GameMap } from '../model/GameMap.js'
import { BabylonUI} from './BabylonUI.js'

/*
    This class is specifically for setting up Babylon base
    components. Nothing custom for the game's UI or the game's
    tiles etc may go here
*/
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

        // BabylonUI.createTopPowerBar()
        BabylonUI.createBotPowerBar()
        BabylonUI.createOwnHealthBar()

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
        BabylonUI.createGui()

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
        } else {
            console.log("No material found: " + skin)
        }
        plane.visibility = true

    }

}
