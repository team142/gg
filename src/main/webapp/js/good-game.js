
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

function createMap(arr) {
    var l = arr.length;
    for (var i = 0; i < l; i++) {
        bUtils.createMapTile(arr[i].x, arr[i].z, arr[i].skin);

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
    engine.runRenderLoop(function () {
        scene.render();
    });
    window.addEventListener("resize", function () {
        engine.resize();
    });
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

    loadSounds();



}

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
                    bUtils.createSphereIfNotExists(obj.TAGS[i]);
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