import { match } from '../model/Match.js'
import { baby } from '../model/Baby.js'
import { TEXTURES_DIR } from './BabylonTextures.js'

const stopAbles = []
const animations = new Map()
let animationCounter = 1


export class BabylonAnimations {

    static createSpray(tankId, ms) {

        const tank = match.getPlayerByTag(tankId)

        // Create a particle system
        const particleSystem = new BABYLON.ParticleSystem("particles", 2000, baby.scene)

        //Texture of each particle
        particleSystem.particleTexture = new BABYLON.Texture(TEXTURES_DIR + "flare.png", baby.scene)

        // Where the particles come from
        particleSystem.emitter = tank // the starting object, the emitter
        particleSystem.minEmitBox = new BABYLON.Vector3(-1, 0, 0) // Starting all from
        particleSystem.maxEmitBox = new BABYLON.Vector3(1, 0, 0) // To...

        // Colors of all particles
        particleSystem.color1 = new BABYLON.Color4(0.7, 0.8, 1.0, 1.0)
        particleSystem.color2 = new BABYLON.Color4(0.2, 0.5, 1.0, 1.0)
        particleSystem.colorDead = new BABYLON.Color4(0, 0, 0.2, 0.0)

        // Size of each particle (random between...
        particleSystem.minSize = 0.1
        particleSystem.maxSize = 0.5

        // Life time of each particle (random between...
        particleSystem.minLifeTime = 0.3
        particleSystem.maxLifeTime = 1.5

        // Emission rate
        particleSystem.emitRate = 1500

        // Blend mode : BLENDMODE_ONEONE, or BLENDMODE_STANDARD
        particleSystem.blendMode = BABYLON.ParticleSystem.BLENDMODE_ONEONE

        // Set the gravity of all particles
        particleSystem.gravity = new BABYLON.Vector3(0, -9.81, 0)

        // Direction of each particle after it has been emitted
        particleSystem.direction1 = new BABYLON.Vector3(-7, 8, 3)
        particleSystem.direction2 = new BABYLON.Vector3(7, 8, -3)

        // Angular speed, in radians
        particleSystem.minAngularSpeed = 0
        particleSystem.maxAngularSpeed = Math.PI

        // Speed
        particleSystem.minEmitPower = 1
        particleSystem.maxEmitPower = 3
        particleSystem.updateSpeed = 0.005

        const animate = function (particles) {
            for (var index = 0; index < particles.length; index++) {
                var particle = particles[index]
                particle.age += this._scaledUpdateSpeed

                // change direction to return to emitter
                if (particle.age >= particle.lifeTime / 2) {
                    var oldLength = particle.direction.length()
                    var newDirection = this.emitter.position.subtract(particle.position)
                    particle.direction = newDirection.scale(3)
                }

                if (particle.age >= particle.lifeTime) { // Recycle
                    particles.splice(index, 1)
                    this._stockParticles.push(particle)
                    index--
                    continue

                } else {
                    particle.colorStep.scaleToRef(this._scaledUpdateSpeed, this._scaledColorStep)
                    particle.color.addInPlace(this._scaledColorStep)

                    if (particle.color.a < 0) {
                        particle.color.a = 0
                    }

                    particle.angle += particle.angularSpeed * this._scaledUpdateSpeed

                    particle.direction.scaleToRef(this._scaledUpdateSpeed, this._scaledDirection)
                    particle.position.addInPlace(this._scaledDirection)

                    this.gravity.scaleToRef(this._scaledUpdateSpeed, this._scaledGravity)
                    particle.direction.addInPlace(this._scaledGravity)
                }
            }
        }
        particleSystem.updateFunction = animate
        particleSystem.start()
        animationCounter++
        let localCounter = animationCounter
        animations.set(localCounter, particleSystem)
        setTimeout(() => {
            const pSystem = animations.get(localCounter)
            pSystem.stop()
            animations.delete(localCounter)
        }, ms)

    }

}