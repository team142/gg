import { baby } from './baby.js'

export class GameMap {

    static create(obj) {
        baby.gameMap = new GameMap(obj)
        return baby.gameMap
    }
    constructor(obj) {
        this.tiles = obj.map
        this.x = obj.x
        this.z = obj.z

    }

}