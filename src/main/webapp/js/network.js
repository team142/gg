import { View } from 'view';


export  function NetworkHandler(){

    var socket;
    var view = new View();
    
    function connectToWebsocket(path){
        console.log('Hello');
    }

    function joinServer(url, name) {
        //Join on websocket...
        userState.username = name;
        socket = new WebSocket("ws://localhost:8080/websocket");
        assignMethods();
    }   
    
    function assignMethods() {
        socket.onopen = function (event) {
            var body = {
                conversation: "P_REQUEST_JOIN_SERVER",
                name: userState.username
            }
            var json = JSON.stringify(body);
            socket.send(json);
    
        }
        socket.onmessage = function (event) {
    
            var obj = JSON.parse(event.data);
            var conversation = obj.conversation;
            if (conversation == "S_CHANGE_VIEW") {
                view.changeView(obj.view);
                return;
            } else if (conversation == "S_LIST_OF_GAMES") {
                showListOfGames(obj.games);
                return;
            }
            // alert("Unhandled message" + event.data);
        }
    }    

    function send(msg){
        socket.send(msg);
    }

    return {
        connectToWebsocket: connectToWebsocket,
        send: send,
        joinServer: joinServer
    }
}
