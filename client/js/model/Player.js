import {match} from './Match'

export class Player {

    static create(tag) {
        const player = new Player()
        match.players.set(tagId, player)
        return player
    }

    static getPlayer(tag) {
        return match.players.get(tag)
    }

    constructor(tagId) {
        this.tag = tagId
        this.name = tagId
    }
}

