
class Match {
    constructor() {
        this.playerTanks = new Map()
        this.gameInstance = Math.floor(Math.random() * 1000)
        this.username = "Chop"
        this.tag = -1
    }

    static getPlayerByTag(tagId) {
        return match.playerTanks.get(tagId)

    }
}

const match = new Match()

