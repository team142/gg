import {
    baby
} from './Baby.js'

const MIN_SIN_VAL = 1.57
const MAX_SIN_VAL = 7.85
const VAL_INC = 0.0785
const tick = 25

export class Orb {

    constructor(name, x, z) {
        this.item = baby.baseRandomOrb.clone(name)
        this.item.position.x = x
        this.item.position.z = z
        this.item.visibility = true

        this.val = MAX_SIN_VAL
        this.ticker()
        this.startTimer()

    }

    startTimer() {
        this.timer = setInterval(
            () => {
                this.ticker()
            }, tick
        )
    }

    ticker() {
        this.item.position.y = (Math.sin(this.val) / 2) + 0.75
        this.val += VAL_INC
        if (this.val > MAX_SIN_VAL) {
            this.val = MIN_SIN_VAL
        }
    }

    stop() {
        clearInterval(this.timer)

    }

}