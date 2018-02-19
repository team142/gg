import { baby } from '../model/Baby.js'

export const TEXTURES_DIR = "textures/"

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

export class BabylonTextures {

    static createMaterials() {

        for (const file of textureFiles) {
            BabylonTextures.createAndSaveMaterial(TEXTURES_DIR + file)
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

        const material = new BABYLON.StandardMaterial("kosh", baby.scene)
        material.diffuseColor = new BABYLON.Color3(0, 0, 0)
        material.emissiveColor = BABYLON.Color3.White()
        material.specularPower = 64
        material.alpha = 0.2
    
        // Material for Random orbs
        material.emissiveFresnelParameters = new BABYLON.FresnelParameters()
        material.emissiveFresnelParameters.bias = 0.2
        material.emissiveFresnelParameters.leftColor = BABYLON.Color3.White()
        material.emissiveFresnelParameters.rightColor = BABYLON.Color3.Gray()
    
        material.opacityFresnelParameters = new BABYLON.FresnelParameters()
        material.opacityFresnelParameters.power = 4
        material.opacityFresnelParameters.leftColor = BABYLON.Color3.White()
        material.opacityFresnelParameters.rightColor = BABYLON.Color3.Black()

        baby.randomOrbMaterial = material
    }

}