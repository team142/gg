var socket;


function joinServer(url, name) {
    username = name;
    socket = new WebSocket("ws://" + url + "websocket");
    assignMethods();

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
                    baby.createSphereIfNotExists(obj.TAGS[i]);
                }
            }


        } else if (conversation == "S_SHARE_TAG") {
            tag = obj.tag;

        } else if (conversation == "S_SHARE_MAP") {
            baby.createMap(obj.MAP);

        } else if (conversation == "S_PLAY_SOUND") {
            GSound.playSound(obj.FILE);

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

