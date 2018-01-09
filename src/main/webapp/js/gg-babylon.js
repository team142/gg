
var baby = {}
var canvas
var engine
var sphere

var DIR = {
    x: 0,
    y: 0,
    z: 0
}
var scene
var ground
var materialsMap = new Map()
var camera

var baseTile
var mountainTile



baby.setup3D = function () {
    canvas = document.getElementById("VIEW_CANVAS")
    engine = new BABYLON.Engine(canvas, true)
    scene = baby.createScene()
    engine.runRenderLoop(function () {
        scene.render()
    })
    window.addEventListener("resize", function () {
        engine.resize()
    })
    window.addEventListener("keyup", function (data) {
        sio.sendKeyUp(data.key)

        var key = data.key
        if (key === "a" || key === "A") {
            DIR.x = 0
        } else if (key === "d" || key === "D") {
            DIR.x = 0
        } else if (key === "s" || key === "S") {
            DIR.z = 0
        } else if (key === "w" || key === "W") {
            DIR.z = 0
        }

    })
    window.addEventListener("keydown", function (data) {
        sio.sendKeyDown(data.key)

        var key = data.key
        if (key === "a" || key === "A") {
            DIR.x = -1
        } else if (key === "d" || key === "D") {
            DIR.x = 1
        } else if (key === "s" || key === "S") {
            DIR.z = -1
        } else if (key === "w" || key === "W") {
            DIR.z = 1
        }

    })

    baby.createBaseTile()
    baby.createMountainTile()
    gSound.loadSounds()
    baby.createSkyBox()
    // var t = setInterval(movementTick, 40)

}

baby.createSkyBox = function () {
    // Skyboxes
	var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", scene)
    skyboxMaterial.backFaceCulling = false
    skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("textures/skybox", scene)
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE
    skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0)
    skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0)
    skyboxMaterial.disableLighting = true
    var skybox1 = BABYLON.Mesh.CreateBox("skyBox1", 50*50, scene)
    skybox1.material = skyboxMaterial
    skybox1.visibility = 0.5
    
}

baby.createSphereIfNotExists = function (tagId) {
    if (tagId) {
        var result = getPlayerByTag(tagId)
        if (!result) {
            var name = "player" + match.gameInstance
            name = name + tagId
            var item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, scene)
            item.position.y = 1
            match.playerTanks.set(tagId, mapItem)

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
    textureFiles.forEach(file => {
        baby.createAndSaveMaterial("/textures/" + file)
    })

}


baby.createAndSaveMaterial = function (textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene)
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, scene)
    materialPlane.diffuseTexture.uScale = 1.0//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false//Always show the front and the back of an element
    materialsMap.set(textureFilePath, materialPlane)

}

baby.createMap = function (arr) {
    arr.forEach(t => {
        baby.createMapTile(t.x, t.z, t.skin, t.model)
    })

}

baby.createMapTile = function (x, y, skin, model) {
    var plane;
    if (model == "FLAT_TILE") {
        plane = baseTile.clone(("plane" + x) + y)
    } else if (model == "ROCK_TILE") {
        plane = mountainTile.clone(("plane" + x) + y)
    }
    plane.position.z = (y * 1)
    plane.position.x = (x * 1)
    plane.rotation.x = Math.PI / 2
    var material = materialsMap.get(skin)
    if (material) {
        plane.material = material
    }

}

baby.createBaseTile = function () {
    var x = -1
    var y = -1
    var skin = "/textures/water1-min.jpg"
    baseTile = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, scene)
    baseTile.position.z = (y * 1)
    baseTile.position.x = (x * 1)
    baseTile.rotation.x = Math.PI / 2
    var material = materialsMap.get(skin)
    if (material) {
        baseTile.material = material;
    }

}

baby.createMountainTile = function () {
    var x = -2
    var y = -2
    var skin = "/textures/rock1-min.jpg"

    mountainTile = BABYLON.MeshBuilder.CreateBox(("plane" + x) + y, { height: 1, width: 1, depth: 1 }, scene);

    // mountainTile = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, scene)
    mountainTile.position.z = (y * 1)
    mountainTile.position.x = (x * 1)
    mountainTile.position.y = 0.5
    mountainTile.rotation.x = Math.PI / 2
    var material = materialsMap.get(skin)
    if (material) {
        mountainTile.material = material;
    }

}

baby.createGui = function () {
    // GUI
    var advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI")

    var button1 = BABYLON.GUI.Button.CreateSimpleButton("but1", "Airstrike")
    button1.width = "125px"
    button1.height = "40px"
    button1.color = "white"
    button1.cornerRadius = 20
    button1.background = "green"
    button1.top = "0px"
    button1.left = "0px"
    button1.onPointerUpObservable.add(function () {
        sphere.position.y = sphere.position.y + 0.1
        // alert("you did it!")
    })
    // advancedTexture.addControl(button1)

    var button2 = BABYLON.GUI.Button.CreateSimpleButton("but2", "Radar")
    button2.width = "125px"
    button2.height = "40px"
    button2.color = "white"
    button2.cornerRadius = 20
    button2.background = "green"
    button2.top = "5px"
    button2.left = "0px"
    button2.onPointerUpObservable.add(function () {

        sphere.position.y = sphere.position.y - 0.1
        // alert("you did it!")
    })

    var panel3 = new BABYLON.GUI.StackPanel()
    panel3.width = "220px"
    panel3.fontSize = "14px"
    panel3.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT
    panel3.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER
    advancedTexture.addControl(panel3)
    panel3.addControl(button1)
    panel3.addControl(button2)

}

baby.createScene = function () {
    var scene = new BABYLON.Scene(engine)
    scene.name = "scene"
    camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene)
    camera.setTarget(BABYLON.Vector3.Zero())
    camera.position.y = 0.75
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene)
    light.intensity = 0.7
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