
let gameMap
export class GameMap {

    static create(obj) {
        gameMap = new GameMap(obj)
    }
    constructor(obj) {
        this.tiles = obj.map
        this.x = obj.x
        this.z = obj.z

    }

}