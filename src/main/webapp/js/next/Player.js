/*
    This file is not currently being used. 
    The idea is to figure out what a player is
    and hold the state here.

*/

class Players {
    
    static create(game, scene, tag, title) {
        game.players.set(tag, new Player(scene, tag, title))
    }

    static remove(game, tag) {
        const player = game.players.get(tag)
        if (player) {
            player.removeFromScene()
            game.players.delete(tag)
        }
    }
    
}

class Player {

    constructor(scene, tag, title) {
        this.tagId = tag
        this.title = title

        this.tank = createMyTank(scene)
        this.rectangle = createMyRectangle()
        this.label = createMyLabel(this.rectangle, this.tank)
        linkLabelToRectangleAndTank(this.label, this.rectangle, this.tank)
        
    }

    createMyTank(scene) {
        const name = "player" + match.gameInstance + this.tagId
        const item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, scene)
        item.position.y = 1
        item.material = baby.smileyMaterial
        return item

    }

    createMyRectangle() {
        const rectText = new BABYLON.GUI.Rectangle()
        rectText.width = 0.2
        rectText.height = "100px"
        rectText.cornerRadius = 20
        rectText.thickness = 0
        baby.advancedTexture.addControl(rectText)
        return rectText

    }

    createMyLabel(rectText, tank) {
        const label = new BABYLON.GUI.TextBlock()
        label.text = this.title

    }

    linkLabelToRectangleAndTank(label, rect, tank) {
        rect.addControl(label)
        rect.linkWithMesh(tank)
        rect.linkOffsetY = -50
        
    }

    removeFromScene() {
        this.label.dispose()
        this.rectangle.dispose()
        this.tank.dispose()

    }

}