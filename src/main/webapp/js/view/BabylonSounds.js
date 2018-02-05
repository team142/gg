import { baby } from '../model/Baby.js'

const sounds = [
    "sounds/pew.mp3",
    "sounds/ding.mp3",
    "sounds/explode.mp3",
    "sounds/shhha.mp3"
]

export class BabylonSounds {
    static loadSounds() {
        for (const s of sounds) {
            BabylonSounds.loadSound(s)
        }
    }

    static loadSound(path) {
        const sound = new BABYLON.Sound(path, path, baby.scene)
        baby.soundData.soundMap.set(path, sound)
    }

    static playSound(key) {
        baby.soundData.soundMap.get(key).play()
    }

    static playPew() {
        BabylonSounds.playSound("sounds/pew.mp3")
    }

}
