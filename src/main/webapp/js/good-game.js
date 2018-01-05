
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
var socket;
var username;

let materials = [];

var tag = -1;
var camera;
var playerTanks = [];
var gameInstance = Math.floor(Math.random() * 1000);


var sounds = [];

function buttonJoinServer() {
    document.getElementById("btnJoinServer").disabled = true;
    var url = document.getElementById("selectServer").value;
    var name = document.getElementById("inputName").value;
    if (name) {
        document.getElementById("btnJoinServer").enabled = false;
        joinServer(url, name);
    }
}

function joinServer(url, name) {
    username = name;
    socket = new WebSocket("ws://" + url + "websocket");
    assignMethods();

}

function showListOfGames(games) {
    var body = {
        conversation: "P_REQUEST_JOIN_GAME",
        id: games[0].id
    }
    var json = JSON.stringify(body);
    socket.send(json);

}

var createScene = function () {

    var scene = new BABYLON.Scene(engine);
    scene.name = "scene";

    camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene);
    camera.setTarget(BABYLON.Vector3.Zero());
    camera.position.y = 0.5;

    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);
    light.intensity = 0.7;


    createMaterials();
    createGui();

    loadSounds();


    return scene;

};

function loadSounds() {
    loadSound("sounds/pew.mp3");

}

function loadSound(path) {
    var sound = new BABYLON.Sound(path, path, scene);
    var item = {
        key: path,
        value: sound
    }
    sounds.push(item);

}

function createMap(arr) {
    var l = arr.length;
    for (var i = 0; i < l; i++) {
        createMapTile(arr[i].x, arr[i].z, arr[i].skin);

    }

}

function createMapTile(x, y, skin) {
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





function createMaterials() {
    //Grass
    createAndSaveMaterial("/textures/grass1.jpg");
    createAndSaveMaterial("/textures/grass2.jpg");
    createAndSaveMaterial("/textures/grass3.jpg");
    createAndSaveMaterial("/textures/rock1.jpg");
    createAndSaveMaterial("/textures/rock2.jpg");
    createAndSaveMaterial("/textures/rock3.jpg");
    createAndSaveMaterial("/textures/water1.jpg");
    createAndSaveMaterial("/textures/water2.jpg");
    createAndSaveMaterial("/textures/water3.jpg");

}

function createGui() {
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

// function getRandomGrassMater() {
//     var l = grassMaterials.length;
//     var i = Math.floor((Math.random() * l) + 1);
//     i--;
//     return grassMaterials[i];

// }

function createSphereIfNotExists(tagId) {
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

function getPlayerByTag(tagId) {
    var l = playerTanks.length;
    for (var i = 0; i < l; i++) {
        if (playerTanks[i].key == tagId) {
            return playerTanks[i].value;
        }
    }
    return;

}


function setup3D() {
    canvas = document.getElementById("VIEW_CANVAS");
    engine = new BABYLON.Engine(canvas, true);
    scene = createScene()
    // var materialPlane = createMaterial("/textures/grass1.jpg");
    // ground.material = materialPlane;

    engine.runRenderLoop(function () {
        scene.render();
    });

    // Resize
    window.addEventListener("resize", function () {
        engine.resize();
    });

    // scene.clearColor = new BABYLON.Color3(91 / 255, 203 / 255, 234 / 255);



    // var t = setInterval(tick, 1000);



    var boxCloud = BABYLON.Mesh.CreateSphere("boxCloud", 100, 100, scene);
    boxCloud.position = new BABYLON.Vector3(0, 0, 12);
    var cloudMaterial = new BABYLON.StandardMaterial("cloudMat", scene);
    var cloudProcText = new BABYLON.CloudProceduralTexture("cloud", 1024, scene);
    cloudMaterial.emissiveTexture = cloudProcText;
    cloudMaterial.backFaceCulling = false;
    cloudMaterial.emissiveTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
    boxCloud.material = cloudMaterial;


}


window.addEventListener("keyup", function (data) {
    sendKeyUp(data.key);
    // var key = data.key;
    // if (key === "a" || key === "A") {
    //     DIR.x = 0;
    // } else if (key === "d" || key === "D") {
    //     DIR.x = 0;
    // } else if (key === "s" || key === "S") {
    //     DIR.z = 0;
    // } else if (key === "w" || key === "W") {
    //     DIR.z = 0;
    // }
});

window.addEventListener("keydown", function (data) {
    sendKeyDown(data.key);
    // var key = data.key;
    // if (key === "a" || key === "A") {
    //     DIR.x = -1;
    // } else if (key === "d" || key === "D") {
    //     DIR.x = 1;
    // } else if (key === "s" || key === "S") {
    //     DIR.z = -1;
    // } else if (key === "w" || key === "W") {
    //     DIR.z = 1;
    // }
});

function sendKeyUp(key) {
    var message = {
        conversation: "P_KU",
        key: key
    }
    send(JSON.stringify(message));
}
function sendKeyDown(key) {
    var message = {
        conversation: "P_KD",
        key: key
    }
    send(JSON.stringify(message));
}


function tick() {
    // sphere.position.x += (0.05 * DIR.x);
    // sphere.position.y += (0.05 * DIR.y);
    // sphere.position.z += (0.05 * DIR.z);
    // camera.position.x = sphere.position.x;
    // camera.position.z = sphere.position.z;

}

function createAndSaveMaterial(textureFilePath) {
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

function joinServer(url, name) {
    //Join on websocket...
    username = name;
    socket = new WebSocket("ws://localhost:8080/websocket");
    assignMethods();
}

function assignMethods() {
    socket.onopen = function (event) {
        var body = {
            conversation: "P_REQUEST_JOIN_SERVER",
            name: username
        }
        var json = JSON.stringify(body);
        socket.send(json);

    }

    socket.onmessage = function (event) {

        var obj = JSON.parse(event.data);
        var conversation = obj.conversation;
        if (conversation == "S_CHANGE_VIEW") {
            changeView(obj.view);

        } else if (conversation == "S_LIST_OF_GAMES") {
            showListOfGames(obj.games);


        } else if (conversation == "S_SCOREBOARD") {
            //First check who is missing from client
            var l = obj.TAGS.length;
            for (var i = 0; i < l; i++) {
                var item = getPlayerByTag(obj.TAGS[i]);
                if (!item) {
                    createSphereIfNotExists(obj.TAGS[i]);
                }
            }


        } else if (conversation == "S_SHARE_TAG") {
            tag = obj.tag;

        } else if (conversation == "S_SHARE_MAP") {
            createMap(obj.MAP);

        } else if (conversation == "S_PLAY_SOUND") {
            playSound(obj.FILE);

        } else if (conversation == "S_PLAYER_LEFT") {

            var tagToRemove = obj.tag;
            var l = playerTanks.length;
            var indexToRemove = -1;
            for (var i = 0; i < l; i++) {
                var key = playerTanks[i].key;
                if (key == tagToRemove) {
                    indexToRemove = i;
                    playerTanks[i].value.dispose();
                }
            }
            if (indexToRemove > -1) {
                playerTanks.splice(indexToRemove, 1);
            }


        } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
            var l = obj.THINGS.length;
            for (var i = 0; i < l; i++) {
                if (obj.THINGS[i].tag == tag) {
                    // if (camera) {
                    camera.position.x = obj.THINGS[i].x;
                    camera.position.y = obj.THINGS[i].y + 0.25;
                    camera.position.z = obj.THINGS[i].z;
                    camera.rotation.y = obj.THINGS[i].rotation;
                    // }
                }
                var s = getPlayerByTag(obj.THINGS[i].tag);
                if (s) {
                    s.position.x = obj.THINGS[i].x;
                    s.position.y = obj.THINGS[i].y;
                    s.position.z = obj.THINGS[i].z;
                    s.rotation.y = obj.THINGS[i].rotation;

                }
            }

        }
        // alert("Unhandled message" + event.data);
    }
}

function send(msg) {
    socket.send(msg);
}

function changeView(view) {
    toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
    toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
    toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
    if (view == "VIEW_CANVAS") {
        setup3D();
        // var t = setInterval(tick, 1000);
    }
}

function toggleElement(id, toggle) {
    if (toggle) {
        document.getElementById(id).style.display = "block"
        document.getElementById(id).style.visibility = "visible";
    } else {
        document.getElementById(id).style.visibility = "hidden";
        document.getElementById(id).style.display = "none"
    }
}


function appStart() {
    toggleElement("VIEW_CANVAS", false)
    toggleElement("VIEW_GAMES", false)
}
window.onload = appStart;


function playSound(key) {
    var snd = sounds.find(function (item) {
        return item.key == key;
    });
    if (snd) {
        snd.play();
    }

}