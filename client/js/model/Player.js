
export class Player {

    static create(tag) {
        const player = new Player()
        players.set(tagId, player)
        return player
    }

    static getPlayer(tag) {
        return players.get(tag)
    }

    constructor(tagId) {
        this.tag = tagId
        this.name = tagId
    }
}

const players = new Map()
