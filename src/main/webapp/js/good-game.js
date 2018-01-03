
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




function buttonSendWebsocket() {
    networkHandler.send(document.getElementById("inputDebug").value);
}


function buttonJoinServer() {
    var url = ""; // TODO
    var name = document.getElementById("inputName").value;
    if (name) {
        document.getElementById("btnJoinServer").enabled = false;
        joinServer(url, name);
    }
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
            name: gameState.username
        }
        var json = JSON.stringify(body);
        socket.send(json);

    }
    socket.onmessage = function (event) {

        var obj = JSON.parse(event.data);
        var conversation = obj.conversation;
        if (conversation == "S_CHANGE_VIEW") {
            changeView(obj.view);
            return;
        } else if (conversation == "S_LIST_OF_GAMES") {
            showListOfGames(obj.games);
            return;
        }



        // alert("Unhandled message" + event.data);
    }
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
    sphere = BABYLON.Mesh.CreateSphere("sphere1", 16, 2, scene);

    // Move the sphere upward 1/2 its height
    sphere.position.y = 1;
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

    for (var x = 1; x <= 20; x++) {
        for (var y = 1; y <= 20; y++) {
            // console.log("x: " + x + ", y: " + y);
            createMapTile(x, y);
        }
    }




    // GUI
    var advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");

    var button1 = BABYLON.GUI.Button.CreateSimpleButton("but1", "Up");
    button1.width = "75px"
    button1.height = "40px";
    button1.color = "white";
    button1.cornerRadius = 20;
    button1.background = "green";
    button1.top = "0px";
    button1.left = "-85px";
    button1.onPointerUpObservable.add(function () {
        sphere.position.y = sphere.position.y + 0.1
        // alert("you did it!");
    });
    advancedTexture.addControl(button1);

    var button2 = BABYLON.GUI.Button.CreateSimpleButton("but2", "Down");
    button2.width = "75px"
    button2.height = "40px";
    button2.color = "white";
    button2.cornerRadius = 20;
    button2.background = "green";
    button2.top = "0px";
    button2.left = "10px";
    button2.onPointerUpObservable.add(function () {

        sphere.position.y = sphere.position.y - 0.1
        // alert("you did it!");
    });
    advancedTexture.addControl(button2);

    // button2.position.y += button2.position.y + 1;



    return scene;

};

function getRandomGrassMater() {
    var l = grassMaterials.length;
    var i = Math.floor((Math.random() * l) + 1);
    i--;
    return grassMaterials[i];

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

    var t = setInterval(tick, 20);
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
    sphere.position.x += (0.05 * DIR.x);
    sphere.position.y += (0.05 * DIR.y);
    sphere.position.z += (0.05 * DIR.z);


    camera.position.x = sphere.position.x;
    camera.position.z = sphere.position.z;


}

function createMaterial(textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, scene);
    materialPlane.diffuseTexture.uScale = 1.0;//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0;//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false;//Always show the front and the back of an element
    return materialPlane;
}



function connectToWebsocket(path) {
    console.log('Hello');
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
            return;
        } else if (conversation == "S_LIST_OF_GAMES") {
            showListOfGames(obj.games);
            return;
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


