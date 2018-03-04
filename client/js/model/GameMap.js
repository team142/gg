import { match } from './Match.js'

export class GameMap {

    static create(obj) {
        match.gameMap = new GameMap(obj)
        return match.gameMap
    }
    constructor(obj) {
        this.tiles = obj.map
        this.maxX = obj.maxX
        this.maxZ = obj.maxZ

    }

}