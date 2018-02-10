import { BabylonUtils } from './BabylonUtils.js'
import { game } from '../model/Game.js'
import { baby } from '../model/Baby.js'
import { match } from '../model/Match.js'

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
                baby.camera.position.x = t.x
                baby.camera.position.y = t.y + 0.25
                baby.camera.position.z = t.z
                baby.camera.rotation.y = t.rotation

                //Move the healthbar
                BabylonUtils.changeMyHealthBar(t.health, t.maxHealth)
            }
            const s = match.getPlayerByTag(t.tag)
            if (s) {
                s.position.x = t.x
                s.position.y = t.y
                s.position.z = t.z
                s.rotation.y = t.rotation - 1.57
            }
            const rect1 = match.getHealthBarByTag(t.tag)
            if (rect1) {
                BabylonUtils.setHealthRectangle(rect1, t.health, t.maxHealth)
            }


        }

    }

}