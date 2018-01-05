
var playerTanks = [];
var gameInstance = Math.floor(Math.random() * 1000);
var username;
var tag = -1;


function getPlayerByTag(tagId) {
    var l = playerTanks.length;
    for (var i = 0; i < l; i++) {
        if (playerTanks[i].key == tagId) {
            return playerTanks[i].value;
        }
    }
    return;

}