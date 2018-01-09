
var match = {
    playerTanks: new Map(),
    gameInstance: Math.floor(Math.random() * 1000),
    username: "Chop",
    tag: -1
}

function getPlayerByTag(tagId) {
    return match.playerTanks.get(tagId);

}