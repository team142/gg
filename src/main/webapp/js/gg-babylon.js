
var baby = {
    materialsMap: new Map(),
    DIR: {
        x: 0,
        y: 0,
        z: 0
    },
    textScores: [],
    counter: 0

}

baby.getCounter = function () {
    baby.counter++
    return baby.counter

}


baby.setup3D = function () {
    baby.canvas = document.getElementById("VIEW_CANVAS")
    baby.engine = new BABYLON.Engine(baby.canvas, true)
    baby.scene = baby.createScene()
    baby.engine.runRenderLoop(function () {
        baby.scene.render()
    })
    window.addEventListener("resize", function () {
        baby.engine.resize()
    })
    window.addEventListener("keyup", function (data) {
        sio.sendKeyUp(data.key)

        var key = data.key
        if (key === "a" || key === "A") {
            baby.DIR.x = 0
        } else if (key === "d" || key === "D") {
            baby.DIR.x = 0
        } else if (key === "s" || key === "S") {
            baby.DIR.z = 0
        } else if (key === "w" || key === "W") {
            baby.DIR.z = 0
        }

    })
    window.addEventListener("keydown", function (data) {
        sio.sendKeyDown(data.key)

        var key = data.key
        if (key === "a" || key === "A") {
            baby.DIR.x = -1
        } else if (key === "d" || key === "D") {
            baby.DIR.x = 1
        } else if (key === "s" || key === "S") {
            baby.DIR.z = -1
        } else if (key === "w" || key === "W") {
            baby.DIR.z = 1
        }

    })

    baby.createBaseTile()
    baby.createMountainTile()
    gSound.loadSounds()
    baby.createSkyBox()
    baby.baseBullet = baby.createBaseBullet()
    // var t = setInterval(movementTick, 40)

}

baby.createSkyBox = function () {
    // Skyboxes
    var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", baby.scene)
    skyboxMaterial.backFaceCulling = false
    skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("textures/skybox", baby.scene)
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE
    skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0)
    skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0)
    skyboxMaterial.disableLighting = true
    var skybox1 = BABYLON.Mesh.CreateBox("skyBox1", 50 * 50, baby.scene)
    skybox1.material = skyboxMaterial
    skybox1.visibility = 1

}

baby.displayScores = function () {
    game.scores.forEach((row, i) => {
        baby.createRightText(i, row.key, row.value)
    })

}

baby.createSphereIfNotExists = function (tagId, labelText) {
    if (tagId) {
        var result = getPlayerByTag(tagId)
        if (!result) {
            var name = "player" + match.gameInstance
            name = name + tagId
            var item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, baby.scene)
            item.position.y = 1
            match.playerTanks.set(tagId, item)

            var rectText = new BABYLON.GUI.Rectangle()
            rectText.width = 0.2
            rectText.height = "100px"
            rectText.cornerRadius = 20
            rectText.thickness = 0
            baby.advancedTexture.addControl(rectText)
            var label = new BABYLON.GUI.TextBlock()
            label.text = labelText
            rectText.addControl(label)
            rectText.linkWithMesh(item)
            rectText.linkOffsetY = -50


            //For now create a smily for each person
            var mat = new BABYLON.StandardMaterial("", baby.scene);
            mat.diffuseTexture = new BABYLON.Texture("textures/smily.png", baby.scene);
            item.material = mat;


        }
    }

}

baby.createMaterials = function () {
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
        baby.createAndSaveMaterial("/textures/" + file)
    }

}


baby.createAndSaveMaterial = function (textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", baby.scene)
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, baby.scene)
    materialPlane.diffuseTexture.uScale = 1.0//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false//Always show the front and the back of an element
    baby.materialsMap.set(textureFilePath, materialPlane)

}

baby.createMap = function (arr) {
    for (const t of arr) {
        baby.createMapTile(t.x, t.z, t.skin, t.model)
    }

}

baby.createBaseBullet = function () {
    var cone = BABYLON.MeshBuilder.CreateCylinder("cone", { diameterTop: 0, height: 1, tessellation: 96 }, baby.scene)
    var scl = 0.0625 //Much smaller than normal cone
    var scalingFactor = new BABYLON.Vector3(scl, scl, scl)
    cone.scaling = scalingFactor
    cone.position.multiplyInPlace(scalingFactor)
    cone.rotation.x = -1.57
    return cone

}

baby.createBullet = function (obj) {
    console.log(obj.BULLET)

    var bullet
    bullet = baby.baseBullet.clone("bullet" + baby.getCounter())
    //Set start location

    bullet.position.x = obj.BULLET.x
    bullet.position.y = obj.BULLET.y
    bullet.position.z = obj.BULLET.z
    bullet.rotation.y = obj.BULLET.rotation + Math.PI

    //Set path

    //Start animation?
}

baby.createMapTile = function (x, y, skin, model) {
    var plane;
    if (model == "FLAT_TILE") {
        plane = baby.baseTile.clone(("plane" + x) + y)
    } else if (model == "ROCK_TILE") {
        plane = baby.mountainTile.clone(("plane" + x) + y)
    }
    plane.position.z = (y * 1)
    plane.position.x = (x * 1)
    plane.rotation.x = Math.PI / 2
    var material = baby.materialsMap.get(skin)
    if (material) {
        plane.material = material
    }
    plane.visibility = true

}

baby.createBaseTile = function () {
    var x = -1
    var y = -1
    var skin = "/textures/water1-min.jpg"
    baby.baseTile = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, baby.scene)
    baby.baseTile.position.z = (y * 1)
    baby.baseTile.position.x = (x * 1)
    baby.baseTile.rotation.x = Math.PI / 2
    var material = baby.materialsMap.get(skin)
    if (material) {
        baby.baseTile.material = material;
    }
    baby.baseTile.visibility = false

}

baby.createMountainTile = function () {
    var x = -2
    var y = -2
    var skin = "/textures/rock1-min.jpg"

    baby.mountainTile = BABYLON.MeshBuilder.CreateBox(("plane" + x) + y, { height: 1, width: 1, depth: 1 }, baby.scene);
    baby.mountainTile.position.z = (y * 1)
    baby.mountainTile.position.x = (x * 1)
    baby.mountainTile.position.y = 0.5
    baby.mountainTile.rotation.x = Math.PI / 2
    var material = baby.materialsMap.get(skin)
    if (material) {
        baby.mountainTile.material = material;
    }
    baby.mountainTile.visibility = false

}

baby.createRightText = function (num, name, score) {
    var current
    if (num < baby.textScores.length) {
        current = baby.textScores[num]
    } else {
        current = new BABYLON.GUI.TextBlock()
        current.color = "white"
        current.fontSize = 24
        current.textHorizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT
        baby.panelScores.addControl(current)
        baby.textScores.push(current)
    }
    current.text = name + ": " + score

}

baby.createGui = function () {
    baby.advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI")

    baby.panelScores = new BABYLON.GUI.StackPanel()
    baby.panelScores.width = "220px"
    baby.panelScores.height = "100px"
    baby.panelScores.fontSize = "14px"
    baby.panelScores.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT
    baby.panelScores.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER
    baby.advancedTexture.addControl(baby.panelScores)

}

baby.createScene = function () {
    var scene = new BABYLON.Scene(baby.engine)
    scene.name = "scene"
    baby.camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene)
    baby.camera.setTarget(BABYLON.Vector3.Zero())
    baby.camera.position.y = 0.75
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene)
    light.intensity = 0.9
    baby.createMaterials()
    baby.createGui()
    return scene

}



// function movementTick() {
//     //TODO
//     var s = getPlayerByTag(tag)
//     if (s) {
//         s.position.x += (0.0625 * DIR.x)
//         s.position.y += (0.0625 * DIR.y)
//         s.position.z += (0.0625 * DIR.z)


//         camera.position.x += (0.0625 * DIR.x)
//         camera.position.z += (0.0625 * DIR.z)
//     }

// }