var username;
var socket;

function buttonSendWebsocket() {
    socket.send(document.getElementById("inputDebug").value);
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



        alert("Unhandled message" + event.data);
    }
}

function showListOfGames(games) {
    
}

function changeView(view) {
    toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
    toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
    toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")

}


function toggleElement(id, toggle) {
    if (toggle) {
        document.getElementById(id).style.visibility = "visible";
    } else {
        document.getElementById(id).style.visibility = "hidden";
    }

}


var canvas = document.getElementById("VIEW_CANVAS");
var engine = new BABYLON.Engine(canvas, true);

var sphere;

var DIR = {
    x: 0,
    y: 0,
    z: 0
}

var createScene = function () {

    // This creates a basic Babylon Scene object (non-mesh)
    var scene = new BABYLON.Scene(engine);

    // This creates and positions a free camera (non-mesh)
    var camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 5, -10), scene);

    // This targets the camera to scene origin
    camera.setTarget(BABYLON.Vector3.Zero());

    // This attaches the camera to the canvas
    camera.attachControl(canvas, true);

    // This creates a light, aiming 0,1,0 - to the sky (non-mesh)
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);

    // Default intensity is 1. Let's dim the light a small amount
    light.intensity = 0.7;

    // Our built-in 'sphere' shape. Params: name, subdivs, size, scene
    // var sphere = BABYLON.Mesh.CreateSphere("sphere1", 16, 2, scene);
    sphere = BABYLON.Mesh.CreateSphere("sphere1", 16, 2, scene);

    // Move the sphere upward 1/2 its height
    sphere.position.y = 1;

    // Our built-in 'ground' shape. Params: name, width, depth, subdivs, scene
    var ground = BABYLON.Mesh.CreateGround("ground1", 6, 6, 2, scene);

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

var scene = createScene()

engine.runRenderLoop(function () {
    scene.render();
});

// Resize
window.addEventListener("resize", function () {
    engine.resize();
});

window.addEventListener("keyup", function (data) {
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

// window.addEventListener("keypress", function(data) {
//     var key = data.key;
//     if (key === "a" || key === "A") {

//         sphere.position.x -= 0.1;
//     } else if (key === "d" || key === "D") {
//         sphere.position.x += 0.1;
//     } else if (key === "s" || key === "S") {
//         sphere.position.z -= 0.1;
//     } else if (key === "w" || key === "W") {
//         sphere.position.z += 0.1;
//     }
//     console.log(key);
// });

function tick() {
    console.log("Tick")

    sphere.position.x += (0.05 * DIR.x);
    sphere.position.y += (0.05 * DIR.y);
    sphere.position.z += (0.05 * DIR.z);
}

// var t = setInterval(tick, 20);
