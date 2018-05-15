import {BabylonUtils} from '../view/BabylonUtils.js'
import {baby} from '../model/Baby.js'

export class Bullet {

    setupTicker() {
        this.bulletTimer = setInterval(() => {
            this.tick()
        }, (17 * 3))

    }

    static createAndSave(obj) {
        baby.bullets.push(new Bullet(obj.BULLET, baby.baseBullet.clone("bullet" + BabylonUtils.getCounter())))
    }

    constructor(obj, babylonObject) {
        this.id = BabylonUtils.getCounter()
        this.sBullet = obj
        this.bBullet = babylonObject
        this.bBullet.visibility = true

        this.bBullet.position.x = +obj.point.x
        this.bBullet.position.y = +obj.point.y
        this.bBullet.position.z = +obj.point.z
        this.bBullet.rotation.y = +obj.point.rotation + Math.PI

        this.tick()
        this.tick()
        this.tick()

        this.setupTicker()
    }

    tick() {

        this.bBullet.position.x += Math.sin(this.sBullet.point.rotation) * this.sBullet.speed * 3
        this.bBullet.position.z += Math.cos(this.sBullet.point.rotation) * this.sBullet.speed * 3

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
        clearInterval(this.bulletTimer)
        this.bBullet.dispose()
        const index = baby.bullets.indexOf(this)
        baby.bullets.splice(index, 1)

    }

}