
let gameMap
export class GameMap {

    create(obj) {
        gameMap = new GameMap(obj)
    }
    constructor(obj) {
        this.tiles = obj.map
        this.x = obj.x
        this.z = obj.z

    }

}