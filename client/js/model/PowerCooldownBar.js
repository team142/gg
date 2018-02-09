
const POWER_ICON_WIDTH = 75

class PowerCooldownBar {

    constructor(babylonRect, ticks) {
        this.ticksToFull = xticksToFull
        this.currentValue = 0
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
                tick()
            }
            , 20
        )
    }

    tick() {
        if (this.currentValue == this.ticksToFull) {
            return
        }

        this.currentValue++
        
        let width = 0
        if (this.currentValue > this.ticksToFull) {
            this.currentValue = this.ticksToFull
            width = 0
        } else {
            width = this.currentValue / this.ticksToFull * POWER_ICON_WIDTH
        }
        this.babylonRect.width = width
        
    }

}