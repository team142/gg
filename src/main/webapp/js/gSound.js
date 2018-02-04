import { baby } from './view/BabylonUtils.js'

export const soundData = {
    soundMap: new Map()

}

export class SoundUtils {
    static loadSounds() {
        SoundUtils.loadSound("sounds/pew.mp3")
        SoundUtils.loadSound("sounds/ding.mp3")
        SoundUtils.loadSound("sounds/explode.mp3")
        SoundUtils.loadSound("sounds/shhha.mp3")

    }

    static loadSound(path) {
        var sound = new BABYLON.Sound(path, path, baby.scene)
        soundData.soundMap.set(path, sound)

    }

    static playSound(key) {
        soundData.soundMap.get(key).play()

    }

    static playPew() {
        SoundUtils.playSound("sounds/pew.mp3")

    }

}
