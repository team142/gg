
var playerTanks = []
var gameInstance = Math.floor(Math.random() * 1000)
var username
var tag = -1

function getPlayerByTag(tagId) {
    return playerTanks.find(function (item) {
        return item.key == tagId
    })
}