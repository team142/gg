
var match = {
    playerTanks: new Map()
    
}

var gameInstance = Math.floor(Math.random() * 1000)
var username
var tag = -1

function getPlayerByTag(tagId) {
    return match.playerTanks.get(tagId);

}