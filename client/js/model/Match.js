
class Match {
    constructor() {
        this.playerTanks = new Map()
        this.playerHealthBars = new Map()
        this.playerLabels = new Map()
        this.playerRectangles = new Map()
        this.gameInstance = Math.floor(Math.random() * 1000)
        this.username = "Unknown"
        this.tag = -1
        this.players = new Map()
        this.miniMapOn = false
        this.scores = []
        this.gameMap = {}

    }

    isMiniMapOn() {
        return this.miniMapOn
    }

    setMiniMapOn(v) {
        this.miniMapOn = v
    }

    playerLeaves(tagId) {

        let children = match.getPlayerByTag(tagId).getChildren()
        children.forEach(child => child.dispose())

        match.getPlayerByTag(tagId).dispose()
        match.playerTanks.delete(tagId)

        match.getPlayerRectangleByTag(tagId).dispose()
        match.playerRectangles.delete(tagId)

        match.playerHealthBars.get(tagId).dispose()
        match.playerHealthBars.delete(tagId)

        match.getPlayerLabelByTag(tagId).dispose()
        match.playerLabels.delete(tagId)

    }

    getPlayerByTag(tagId) {
        return match.playerTanks.get(tagId)

    }

    getHealthBarByTag(tagId) {
        return match.playerHealthBars.get(tagId)

    }

    getPlayerLabelByTag(tagId) {
        return match.playerLabels.get(tagId)

    }

    getPlayerRectangleByTag(tagId) {
        return match.playerRectangles.get(tagId)

    }
}

export const match = new Match()
