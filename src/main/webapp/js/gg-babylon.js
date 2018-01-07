var baby = {};

var canvas;
var engine;
var sphere;

var DIR = {
    x: 0,
    y: 0,
    z: 0
}
var scene;
var ground;
let materials = [];
var camera;


baby.setup3D = function () {
    canvas = document.getElementById("VIEW_CANVAS");
    engine = new BABYLON.Engine(canvas, true);
    scene = baby.createScene()
    engine.runRenderLoop(function () {
        scene.render();
    });
    window.addEventListener("resize", function () {
        engine.resize();
    });
    window.addEventListener("keyup", function (data) {
        sio.sendKeyUp(data.key);

        var key = data.key;
        if (key === "a" || key === "A") {
            DIR.x = 0;
        } else if (key === "d" || key === "D") {
            DIR.x = 0;
        } else if (key === "s" || key === "S") {
            DIR.z = 0;
        } else if (key === "w" || key === "W") {
            DIR.z = 0;
        }

    });




    window.addEventListener("keydown", function (data) {
        sio.sendKeyDown(data.key);

        var key = data.key;
        if (key === "a" || key === "A") {
            DIR.x = -1;
        } else if (key === "d" || key === "D") {
            DIR.x = 1;
        } else if (key === "s" || key === "S") {
            DIR.z = -1;
        } else if (key === "w" || key === "W") {
            DIR.z = 1;
        }

    });

    // scene.clearColor = new BABYLON.Color3(91 / 255, 203 / 255, 234 / 255);

    var boxCloud = BABYLON.Mesh.CreateSphere("boxCloud", 100, 100, scene);
    boxCloud.position = new BABYLON.Vector3(0, 0, 12);
    var cloudMaterial = new BABYLON.StandardMaterial("cloudMat", scene);
    var cloudProcText = new BABYLON.CloudProceduralTexture("cloud", 1024, scene);
    cloudMaterial.emissiveTexture = cloudProcText;
    cloudMaterial.backFaceCulling = false;
    cloudMaterial.emissiveTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
    boxCloud.material = cloudMaterial;

    GSound.loadSounds();
    // var t = setInterval(movementTick, 40);

}

// function movementTick() {
//     //TODO
//     var s = getPlayerByTag(tag);
//     if (s) {
//         s.position.x += (0.0625 * DIR.x);
//         s.position.y += (0.0625 * DIR.y);
//         s.position.z += (0.0625 * DIR.z);


//         camera.position.x += (0.0625 * DIR.x);
//         camera.position.z += (0.0625 * DIR.z);
//     }

// }




baby.createSphereIfNotExists = function (tagId) {
    if (tagId) {
        var result = getPlayerByTag(tagId);
        if (!result) {
            var name = "player" + gameInstance;
            name = name + tagId;
            var item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, scene);
            item.position.y = 1;

            var mapItem = {
                key: tagId,
                value: item
            }

            playerTanks.push(mapItem);

        }
    }
}

baby.createMaterials = function () {
    const textureFiles = [
        "grass1.jpg",
        "grass2.jpg",
        "grass3.jpg",
        "rock1.jpg",
        "rock2.jpg",
        "rock3.jpg",
        "water1.jpg",
        "water2.jpg",
        "water3.jpg"
    ]
    textureFiles.forEach(file => {
        baby.createAndSaveMaterial("/textures/" + file)
    })

}


baby.createAndSaveMaterial = function (textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, scene);
    materialPlane.diffuseTexture.uScale = 1.0;//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0;//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false;//Always show the front and the back of an element
    var item = {
        key: textureFilePath,
        value: materialPlane
    };
    materials.push(item);

}

baby.createMap = function (arr) {
    var l = arr.length;
    for (var i = 0; i < l; i++) {
        baby.createMapTile(arr[i].x, arr[i].z, arr[i].skin);

    }

}

baby.createMapTile = function (x, y, skin) {
    var plane = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, scene);
    plane.position.z = (y * 1);
    plane.position.x = (x * 1);
    plane.rotation.x = Math.PI / 2;
    materials.forEach(function (entry) {
        if (entry.key == skin) {
            plane.material = entry.value;
        }
    });

}



baby.createGui = function () {
    // GUI
    var advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");

    var button1 = BABYLON.GUI.Button.CreateSimpleButton("but1", "Airstrike");
    button1.width = "125px"
    button1.height = "40px";
    button1.color = "white";
    button1.cornerRadius = 20;
    button1.background = "green";
    button1.top = "0px";
    button1.left = "0px";
    button1.onPointerUpObservable.add(function () {
        sphere.position.y = sphere.position.y + 0.1
        // alert("you did it!");
    });
    // advancedTexture.addControl(button1);

    var button2 = BABYLON.GUI.Button.CreateSimpleButton("but2", "Radar");
    button2.width = "125px"
    button2.height = "40px";
    button2.color = "white";
    button2.cornerRadius = 20;
    button2.background = "green";
    button2.top = "5px";
    button2.left = "0px";
    button2.onPointerUpObservable.add(function () {

        sphere.position.y = sphere.position.y - 0.1
        // alert("you did it!");
    });

    var panel3 = new BABYLON.GUI.StackPanel();
    panel3.width = "220px";
    panel3.fontSize = "14px";
    panel3.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT;
    panel3.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER;
    advancedTexture.addControl(panel3);
    panel3.addControl(button1);
    panel3.addControl(button2);

}

baby.createScene = function () {
    var scene = new BABYLON.Scene(engine);
    scene.name = "scene";
    camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene);
    camera.setTarget(BABYLON.Vector3.Zero());
    camera.position.y = 0.5;
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);
    light.intensity = 0.7;
    baby.createMaterials();
    baby.createGui();
    return scene;

};
