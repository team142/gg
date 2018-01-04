
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
let map = new Map([["key1", "value1"], ["key2", "value2"]]);
map.clear();

var grassMaterials = [];
var waterMaterials = [];
var rockMaterials = [];
var tag = -1;
var camera;
var x = {
    a: 0
};
// var players = new Map();
// let players = new Map([["key1", x]]);
// players.clear();
var playerTanks = [];
var gameInstance = Math.floor(Math.random() * 1000);



function buttonSendWebsocket() {
    networkHandler.send(document.getElementById("inputDebug").value);
}


function buttonJoinServer() {
    var url = document.getElementById("selectServer").value;
    var name = document.getElementById("inputName").value;
    if (name) {
        document.getElementById("btnJoinServer").enabled = false;
        joinServer(url, name);
    }
}

function joinServer(url, name) {
    //Join on websocket...
    username = name;
    socket = new WebSocket("ws://" + url + "websocket");
    assignMethods();

}

// function assignMethods() {
//     socket.onopen = function (event) {
//         var body = {
//             conversation: "P_REQUEST_JOIN_SERVER",
//             name: gameState.username
//         }
//         var json = JSON.stringify(body);
//         socket.send(json);

//     }
//     socket.onmessage = function (event) {

//         var obj = JSON.parse(event.data);
//         var conversation = obj.conversation;
//         if (conversation == "S_CHANGE_VIEW") {
//             changeView(obj.view);
//             return;
//         } else if (conversation == "S_LIST_OF_GAMES") {
//             showListOfGames(obj.games);
//             return;
//         }



//         // alert("Unhandled message" + event.data);
//     }
// }

function showListOfGames(games) {
    var body = {
        conversation: "P_REQUEST_JOIN_GAME",
        id: games[0].id
    }
    var json = JSON.stringify(body);
    socket.send(json);

}

var createScene = function () {

    // This creates a basic Babylon Scene object (non-mesh)
    var scene = new BABYLON.Scene(engine);
    scene.name = "scene";

    // This creates and positions a free camera (non-mesh)
    camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene);

    // This targets the camera to scene origin
    camera.setTarget(BABYLON.Vector3.Zero());

    // This attaches the camera to the canvas
    // camera.attachControl(canvas, true);

    // This creates a light, aiming 0,1,0 - to the sky (non-mesh)
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);

    // Default intensity is 1. Let's dim the light a small amount
    light.intensity = 0.7;

    // Our built-in 'sphere' shape. Params: name, subdivs, size, scene
    // var sphere = BABYLON.Mesh.CreateSphere("sphere1", 16, 2, scene);
    // sphere = BABYLON.Mesh.CreateSphere("sphere1", 16, 2, scene);

    // Move the sphere upward 1/2 its height
    // sphere.position.y = 1;
    camera.position.y = 0.5;


    // Our built-in 'ground' shape. Params: name, width, depth, subdivs, scene
    // ground = BABYLON.Mesh.CreateGround("ground1", 6, 6, 2, scene);


    grassMaterials.push(createMaterial("/textures/grass1.jpg"));
    grassMaterials.push(createMaterial("/textures/grass2.jpg"));
    grassMaterials.push(createMaterial("/textures/grass3.jpg"));

    rockMaterials.push(createMaterial("/textures/rock1.jpg"));
    rockMaterials.push(createMaterial("/textures/rock2.jpg"));
    rockMaterials.push(createMaterial("/textures/rock3.jpg"));

    waterMaterials.push(createMaterial("/textures/water1.jpg"));
    waterMaterials.push(createMaterial("/textures/water2.jpg"));
    waterMaterials.push(createMaterial("/textures/water2.jpg"));

    for (var x = 0; x <= 20; x++) {
        for (var y = 0; y <= 20; y++) {
            // console.log("x: " + x + ", y: " + y);
            createMapTile(x, y);
        }
    }




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
    // advancedTexture.addControl(button2);

    // button2.position.y += button2.position.y + 1;



    var panel3 = new BABYLON.GUI.StackPanel();
    panel3.width = "220px";
    panel3.fontSize = "14px";
    panel3.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT;
    panel3.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER;
    advancedTexture.addControl(panel3);
    panel3.addControl(button1);
    panel3.addControl(button2);


    return scene;

};

function getRandomGrassMater() {
    var l = grassMaterials.length;
    var i = Math.floor((Math.random() * l) + 1);
    i--;
    return grassMaterials[i];

}

function createSphereIfNotExists(tagId) {
    if (tagId) {
        var result = getPlayerByTag(tagId);
        if (!result) {
            console.log("Creating a sphere:" + tagId)
            var name = "player" + gameInstance;
            name = name + tagId;
            console.log("Creating sphere with name: " + name);
            if (!scene) {
                console.log("no scene!!");
            }
            var item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, scene);
            item.position.y = 1;

            var mapItem = {
                key: tagId,
                value: item
            }

            playerTanks.push(mapItem);

            // console.log(players);
        }
    } else {
        console.log("No tagId for create sphere");
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

    // var result = players.get(tag);
    // if (!result) {
    //     console.log("Did not find speher:" + tag + ", ");
    //     console.log(players);
    //     return;
    // }
    // console.log("Found existing spehere!!! Tag:" + tag)
    // return result;

}


function createMapTile(x, y) {

    var plane = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, scene);
    plane.position.z = (y * 1);
    plane.position.x = (x * 1);
    plane.rotation.x = Math.PI / 2;
    //Creation of a repeated textured material
    // var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
    // materialPlane.diffuseTexture = new BABYLON.Texture("/textures/grass1.jpg", scene);
    // materialPlane.diffuseTexture.uScale = 1.0;//Repeat 5 times on the Vertical Axes
    // materialPlane.diffuseTexture.vScale = 1.0;//Repeat 5 times on the Horizontal Axes
    // materialPlane.backFaceCulling = false;//Always show the front and the back of an element
    plane.material = getRandomGrassMater();

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

    // scene.clearColor = new BABYLON.Color4.FromHexString("#42E8F4");
    scene.clearColor = new BABYLON.Color3(91 / 255, 203 / 255, 234 / 255);



    // var t = setInterval(tick, 1000);

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

    camera.rotation.y += 0.1;
    console.log(camera.rotation.y);


}

function createMaterial(textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, scene);
    materialPlane.diffuseTexture.uScale = 1.0;//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0;//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false;//Always show the front and the back of an element
    return materialPlane;
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
                    console.log("Found someone on the scoreboard who is not here: " + obj.TAGS[i]);
                    createSphereIfNotExists(obj.TAGS[i]);
                }
            }


        } else if (conversation == "S_SHARE_TAG") {
            tag = obj.tag;
            // console.log("My tag is:" + tag);

        } else if (conversation == "S_PLAYER_LEFT") {

            var tagToRemove = obj.tag;
            console.log("Someone left: " + tagToRemove)
            var l = playerTanks.length;
            var indexToRemove = -1;
            for (var i = 0; i < l; i++) {
                var key = playerTanks[i].key;
                if (key == tagToRemove) {
                    console.log("FOUND Someone left: " + tagToRemove)
                    indexToRemove = i;
                    playerTanks[i].value.dispose();
                }
            }
            if (indexToRemove > -1) {
                console.log("REMOVED Someone left: " + tagToRemove)
                playerTanks.splice(indexToRemove, 1);
            }


        } else if (conversation == "S_SHARE_DYNAMIC_THINGS") {
            // console.log(obj);
            var l = obj.THINGS.length;
            for (var i = 0; i < l; i++) {
                if (obj.THINGS[i].tag == tag) {
                    camera.position.x = obj.THINGS[i].x;
                    camera.position.y = obj.THINGS[i].y + 0.25;
                    camera.position.z = obj.THINGS[i].z;
                    camera.rotation.y = obj.THINGS[i].rotation;
                }
                var s = getPlayerByTag(obj.THINGS[i].tag);
                if (s) {
                    s.position.x = obj.THINGS[i].x;
                    s.position.y = obj.THINGS[i].y;
                    s.position.z = obj.THINGS[i].z;
                    s.rotation.y = obj.THINGS[i].rotation;
                } else {
                    // console.log("Couldnt move: " + obj.THINGS[i].tag);
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
    console.log("Chaning view: " + view)
    toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
    toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
    toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
    if (view == "VIEW_CANVAS") {
        console.log("Setup 3D");
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


function testSp() {
    toggleElement("VIEW_CANVAS", false)
    toggleElement("VIEW_GAMES", false)
}
window.onload = testSp;


var gunshot = new BABYLON.Sound("gunshot", "sounds/pew.m4a", scene);

// window.addEventListener("mousedown", function (evt) {
//     // left click to fire
//     if (evt.button === 0) {
//         gunshot.play();
//     }
// });

window.addEventListener("keydown", function (evt) {
    // Press space key to fire
    if (evt.keyCode === 32) {
        gunshot.play();
    }
});