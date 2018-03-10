import { baby } from '../model/Baby.js'
import { BabylonUtils } from '../view/BabylonUtils.js'
import { match } from '../model/Match.js'
import { BabylonUI } from './BabylonUI.js'
import { TEXTURES_DIR } from './BabylonTextures.js'

export class BabylonModels {

    static recievedDynamicThings(obj) {

        for (const t of obj.things) {
            if (t.tag == match.tag) {
                if (!baby.camera) {
                    return
                }
                baby.camera.position.x = +t.point.x
                baby.camera.position.y = +t.point.y + 0.25
                baby.camera.position.z = +t.point.z
                baby.camera.rotation.y = t.point.rotation

                //Move the healthbar
                // BabylonUI.changeMyHealthBar(t.health, t.maxHealth)
            }
            const s = match.getPlayerByTag(t.tag)
            if (s) {
                s.position.x = +t.point.x
                s.position.y = +t.point.y
                s.position.z = +t.point.z
                s.rotation.y = t.point.rotation - 1.57
            }
            // const rect1 = match.getHealthBarByTag(t.tag)
            // if (rect1) {
            //     BabylonUI.setHealthRectangle(rect1, t.health, t.maxHealth)
            // }


        }

    }
    static loadBaseFlatTile() {
        const x = -1
        const y = -1
        const skin = TEXTURES_DIR + "water1-min.jpg"
        baby.baseTile = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, baby.scene)
        baby.baseTile.position.z = (y * 1)
        baby.baseTile.position.x = (x * 1)
        baby.baseTile.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            baby.baseTile.material = material
        }
        baby.baseTile.visibility = false

    }

    static loadBaseMountainTile() {
        const x = -2
        const y = -2
        const skin = TEXTURES_DIR + "rock1-min.jpg"

        baby.mountainTile = BABYLON.MeshBuilder.CreateBox(("plane" + x) + y, { height: 1, width: 1, depth: 1 }, baby.scene)
        baby.mountainTile.position.z = (y * 1)
        baby.mountainTile.position.x = (x * 1)
        baby.mountainTile.position.y = 0.5
        baby.mountainTile.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            baby.mountainTile.material = material
        }
        baby.mountainTile.visibility = false

    }

    static createSkyBox() {
        // Skyboxes

        const skyboxMaterial = new BABYLON.StandardMaterial("skyBox", baby.scene);
        skyboxMaterial.backFaceCulling = false;
        skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture(TEXTURES_DIR +"TropicalSunnyDay", baby.scene);
        skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
        skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0);
        skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0);

        const skybox = BABYLON.Mesh.CreateBox("skyBox", 200.0, baby.scene);
        skybox.position = baby.camera.position;
        skybox.material = skyboxMaterial;


    }

    static createBaseBullet() {
        const cone = BABYLON.MeshBuilder.CreateCylinder("cone", { diameterTop: 0, height: 1, tessellation: 96 }, baby.scene)
        const scl = 0.0625 //Much smaller than normal cone
        const scalingFactor = new BABYLON.Vector3(scl, scl, scl)
        cone.scaling = scalingFactor
        cone.position.multiplyInPlace(scalingFactor)
        cone.rotation.x = -1.57
        cone.material = baby.matBlack
        baby.baseBullet = cone
        baby.baseBullet.visibility = false

    }

    static createBaseRandomOrb() {
        const orb = BABYLON.Mesh.CreateSphere("baseRandomOrb", 32, 0.5, baby.scene);
        orb.material = baby.randomOrbMaterial
        orb.position.y = 0.25
        orb.visibility = false
        baby.baseRandomOrb = orb

    }


}