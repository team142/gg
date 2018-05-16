import {BabylonUtils} from '../view/BabylonUtils.js'
import {baby} from '../model/Baby.js'

const height = 0.25

export class Bullet {


    static createAndSave(obj) {

        const c = BabylonUtils.getCounter()
        const newBullet = baby.baseBullet.clone("bullet" + c)
        const bullet = new Bullet(obj.BULLET, newBullet)

        let ok = true
        let rX = newBullet.position.x
        let rZ = newBullet.position.z

        if (bullet.sBullet.speed) {
            while (ok) {
                rX += Math.sin(bullet.sBullet.point.rotation) * bullet.sBullet.speed
                rZ += Math.cos(bullet.sBullet.point.rotation) * bullet.sBullet.speed
                if (rX > 50 || rX < 0 || rZ > 50 || rZ < 0) {
                    ok = false
                }
            }
        }

        const path = [
            new BABYLON.Vector3(newBullet.position.x, height, newBullet.position.z),
            new BABYLON.Vector3(rX, height, rZ)
        ]
        const catmullRom = BABYLON.Curve3.CreateCatmullRomSpline(
            path,
            60
        )

        const animationPosition = new BABYLON.Animation("animPos" + c, "position", 30, BABYLON.Animation.ANIMATIONTYPE_VECTOR3, BABYLON.Animation.ANIMATIONLOOPMODE_CONSTANT)
        const keysPosition = []
        for (let [p, item] of catmullRom.getPoints().entries()) {
            keysPosition.push(
                {
                    frame: p,
                    value: item
                }
            )
        }


        animationPosition.setKeys(keysPosition)
        const animationGroup = new BABYLON.AnimationGroup("Group" + c)
        animationGroup.addTargetedAnimation(animationPosition, newBullet)
        animationGroup.onAnimationEndObservable.add(
            function () {
                bullet.removeMe()
            }
        )

        animationGroup.play()

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

    }

    removeMe() {
        this.bBullet.dispose()
    }

}