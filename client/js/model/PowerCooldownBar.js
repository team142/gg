
const POWER_ICON_WIDTH = 75

const powerbarCooldownBars = new Map()

export class PowerCooldownBar {

    static save(index, item) {
        powerbarCooldownBars.set(index, item)
    }
    static get(index) {
        return powerbarCooldownBars.get(index)
    }

    constructor(item, ticks) {
        this.ticksToFull = ticks
        this.currentValue = 0
        this.babylonRect = item
        this.useIt()
    }

    changeRefresh(ticks) {
        this.ticksToFull = ticks
    }

    useIt() {
        this.currentValue = 0
        this.startTimer()
    }

    startTimer() {
        this.timer = window.setInterval(
            () => {
                this.tick()
            }
            , 20
        )
    }

    tick() {
        if (this.currentValue == this.ticksToFull) {
            clearInterval(this.timer)
            return
        }
        this.currentValue++

        let width = 0
        if (this.currentValue > this.ticksToFull) {
            this.currentValue = this.ticksToFull
            width = 0
        } else {
            width = POWER_ICON_WIDTH - this.currentValue / this.ticksToFull * POWER_ICON_WIDTH
        }
        this.babylonRect.width = width.toString() + "px"
        
    }

}