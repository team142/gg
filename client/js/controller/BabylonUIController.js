export class BabylonUIController {

    static changeMyHealthBar(health, maxHealth) {
        let potentialWidth = 8 * 80
        let actualWidth = health / maxHealth * potentialWidth
        let di = potentialWidth - actualWidth

        match.healthBar.width = actualWidth + "px"
        match.healthBar.left = 0 - (di / 2)
    }

    static setHealthRectangle(rect1, health, totalHealth) {
        rect1.width = health / totalHealth * 0.2
        if (match.miniMapOn != rect1.isVisible) {
            rect1.isVisible = match.miniMapOn
        }

    }

    

}