import { baby } from '../model/Baby.js'

export class BabylonTerrain {

    static loadBaseFlatTile() {
        const x = -1
        const y = -1
        const skin = "/textures/water1-min.jpg"
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
        const skin = "/textures/rock1-min.jpg"

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
        const skyboxMaterial = new BABYLON.StandardMaterial("skyBox", baby.scene)
        skyboxMaterial.backFaceCulling = false
        skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("textures/skybox", baby.scene)
        skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE
        skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0)
        skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0)
        skyboxMaterial.disableLighting = true
        const skybox1 = BABYLON.Mesh.CreateBox("skyBox1", 50 * 50, baby.scene)
        skybox1.material = skyboxMaterial
        skybox1.visibility = 1

    }

}