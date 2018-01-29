
class Match {
    constructor() {
        this.playerTanks = new Map()
        this.playerLabels = new Map()
        this.playerRectangles = new Map()
        this.gameInstance = Math.floor(Math.random() * 1000)
        this.username = "Chop"
        this.tag = -1
    }

    static playerLeaves(tagId) {

        let children = Match.getPlayerByTag(tagId).getChildren()
        for (const child of children) {
            child.dispose()
        }
        Match.getPlayerByTag(tagId).dispose()
        match.playerTanks.delete(tagId)
        
        Match.getPlayerRectangleByTag(tagId).dispose()
        match.playerRectangles.delete(tagId)
        
        Match.getPlayerLabelByTag(tagId).dispose()
        match.playerLabels.delete(tagId)
        
    }

    static getPlayerByTag(tagId) {
        return match.playerTanks.get(tagId)

    }

    static getPlayerLabelByTag(tagId) {
        return match.playerLabels.get(tagId)

    }

    static getPlayerRectangleByTag(tagId) {
        return match.playerRectangles.get(tagId)

    }
}

const match = new Match()

