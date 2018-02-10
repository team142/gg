import { BabylonUtils } from '../view/BabylonUtils.js'
import { baby } from '../model/Baby.js'

export const bullets = []
export class Bullet {

    static setupTicker() {
        bulletTimer = setInterval(() => {
            Bullet.tickAll()
        }, (17 * 3))

    }

    static tickAll() {
        bullets.forEach(bullet => bullet.tick())
    }

    static createAndSave(obj) {
        bullets.push(new Bullet(obj.BULLET, baby.baseBullet.clone("bullet" + BabylonUtils.getCounter())))
    }

    constructor(obj, babylonObject) {
        this.id = BabylonUtils.getCounter()
        this.sBullet = obj
        this.bBullet = babylonObject
        this.bBullet.visibility = true

        this.bBullet.position.x = obj.point.x
        this.bBullet.position.y = obj.point.y
        this.bBullet.position.z = obj.point.z
        this.bBullet.rotation.y = obj.point.rotation + Math.PI

        this.tick()
        this.tick()
        this.tick()

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
        this.bBullet.dispose()
        const index = bullets.indexOf(this)
        bullets.splice(index, 1)

    }

}

let bulletTimer
