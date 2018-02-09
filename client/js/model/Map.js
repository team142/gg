
export class Map {
    constructor(obj) {
        this.tiles = obj.arr

    }

    setSize(x, z) {
        this.xSize = x
        this.zSize = z
    }

}