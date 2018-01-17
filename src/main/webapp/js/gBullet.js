
const DirectionTypes = {
    DIR0: 0,
    DIR1: 0.785,
    DIR2: 1.57,
    DIR3: 2.355,
    DIR4: 3.14,
    DIR5: 3.925,
    DIR6: 4.71,
    DIR7: 5.495

}

const bullets = []

this.timer = setInterval(moveBullets, 45)

function moveBullets() {
    for (const bullet of bullets) {
        bullet.tick()
    }
}


class Bullet {


    constructor(obj, babylonObject) {
        this.id = BabylonUtils.getCounter()
        this.sBullet = obj
        this.bBullet = babylonObject

        this.bBullet.position.x = obj.x
        this.bBullet.position.y = obj.y
        this.bBullet.position.z = obj.z
        this.bBullet.rotation.y = obj.rotation + Math.PI

        this.tick()
        this.tick()
        this.tick()

    }

    tick() {

        var multi = 3;

        if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR0)) {
            this.bBullet.position.z = (this.bBullet.position.z + (this.sBullet.speed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR1)) {
            this.bBullet.position.x = (this.bBullet.position.x + (this.sBullet.diagonalspeed * multi))
            this.bBullet.position.z = (this.bBullet.position.z + (this.sBullet.diagonalspeed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR2)) {
            this.bBullet.position.x = (this.bBullet.position.x + (this.sBullet.speed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR3)) {
            this.bBullet.position.x = (this.bBullet.position.x + (this.sBullet.diagonalspeed * multi))
            this.bBullet.position.z = (this.bBullet.position.z - (this.sBullet.diagonalspeed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR4)) {
            this.bBullet.position.z = (this.bBullet.position.z - (this.sBullet.speed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR5)) {
            this.bBullet.position.x = (this.bBullet.position.x - (this.sBullet.diagonalspeed * multi))
            this.bBullet.position.z = (this.bBullet.position.z - (this.sBullet.diagonalspeed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR6)) {
            this.bBullet.position.x = (this.bBullet.position.x - (this.sBullet.speed * multi))

        } else if (this.sBullet.direction == 1 && this.sBullet.rotation == (DirectionTypes.DIR7)) {
            this.bBullet.position.x = (this.bBullet.position.x - (this.sBullet.diagonalspeed * multi))
            this.bBullet.position.z = (this.bBullet.position.z + (this.sBullet.diagonalspeed * multi))

        } else {

        }

        if (this.bBullet.position.x < 0) {
            this.removeMe()
            return
        }
        if (this.bBullet.position.z < 0) {
            this.removeMe()
            return
        }
        if (this.bBullet.position.x > 50 + 1) {
            this.removeMe()
            return
        }
        if (this.bBullet.position.z > 50 + 1) {
            this.removeMe()
            return
        }

    }

    removeMe() {
        this.bBullet.dispose()
        const index = bullets.indexOf(this)
        bullets.splice(index, 1)

    }

}