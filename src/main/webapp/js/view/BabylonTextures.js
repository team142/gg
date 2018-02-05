import { BabylonUtils } from './BabylonUtils.js'
import { baby } from '../model/Baby.js'

export class BabylonTextures {

    static createMaterials() {
        const textureFiles = [
            "grass1-min.jpg",
            "grass2-min.jpg",
            "grass3-min.jpg",
            "rock1-min.jpg",
            "rock2-min.jpg",
            "rock3-min.jpg",
            "water1-min.jpg",
            "water2-min.jpg",
            "water3-min.jpg"
        ]
        for (const file of textureFiles) {
            BabylonTextures.createAndSaveMaterial("/textures/" + file)
        }

        baby.matGrey = new BABYLON.StandardMaterial("matGrey", baby.scene)
        baby.matGrey.diffuseColor = new BABYLON.Color3(0.5, 0.5, 0.5)

        baby.matBlack = new BABYLON.StandardMaterial("matBlack", baby.scene)
        baby.matBlack.diffuseColor = new BABYLON.Color3(0, 0, 0)

        baby.matWing = new BABYLON.StandardMaterial("matWing", baby.scene)
        baby.matWing.diffuseColor = new BABYLON.Color3(0.2, 0.2, 1)



    }

    static createAndSaveMaterial(textureFilePath) {
        const materialPlane = new BABYLON.StandardMaterial("texturePlane", baby.scene)
        materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, baby.scene)
        materialPlane.diffuseTexture.uScale = 1.0//Repeat 5 times on the Vertical Axes
        materialPlane.diffuseTexture.vScale = 1.0//Repeat 5 times on the Horizontal Axes
        materialPlane.backFaceCulling = false//Always show the front and the back of an element
        baby.materialsMap.set(textureFilePath, materialPlane)

    }
    
}