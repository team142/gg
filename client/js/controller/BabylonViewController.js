import { BabylonUtils } from '../view/BabylonUtils.js'
import { game } from '../model/Game.js'
import { baby } from '../model/Baby.js'
import { match } from '../model/Match.js'
import { BabylonUIController} from './BabylonUIController.js'

export class BabylonView {

    static scoreboard(obj) {

        Object.keys(obj.tags)
            .forEach(key => BabylonUtils.createSphereIfNotExists(obj.tags[key], key))

        game.scores = []

        Object.keys(obj.scores)
            .forEach(key => game.scores.push(
                {
                    key: key,
                    value: obj.scores[key]
                }
            ))
        game.scores.sort((a, b) => a.value - b.value)
        BabylonUtils.displayScores()

    }

    static recievedDynamicThings(obj) {

        for (const t of obj.things) {
            if (t.tag == match.tag) {
                if (!baby.camera) {
                    return
                }
                baby.camera.position.x = t.point.x
                baby.camera.position.y = t.point.y + 0.25
                baby.camera.position.z = t.point.z
                baby.camera.rotation.y = t.point.rotation

                //Move the healthbar
                BabylonUIController.changeMyHealthBar(t.health, t.maxHealth)
            }
            const s = match.getPlayerByTag(t.tag)
            if (s) {
                s.position.x = t.point.x
                s.position.y = t.point.y
                s.position.z = t.point.z
                s.rotation.y = t.point.rotation - 1.57
            }
            const rect1 = match.getHealthBarByTag(t.tag)
            if (rect1) {
                BabylonUIController.setHealthRectangle(rect1, t.health, t.maxHealth)
            }


        }

    }

}