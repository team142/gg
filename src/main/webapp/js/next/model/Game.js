/*
    The idea here is to figure out what state must be held by a game
*/

class Game {

    constructor(url, myName, myTag) {
        this.url = url
        this.myName = myName
        this.myTag = myTag

        this.players = new Map()

        createScene()

        loadMaterials()

        loadSounds()

    }

    createScene() {

    }

    loadMaterials() {

    }

    loadSounds() {

    }

}