
export class GameMap {
    constructor(obj) {
        this.tiles = obj.arr
        console.log(obj)

    }

    setSize(x, z) {
        this.xSize = x
        this.zSize = z
    }

}