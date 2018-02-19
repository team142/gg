import {
    baby
} from '../model/Baby.js'

const SOUNDS_DIR = "sounds/"
const sounds = [
    "pew.mp3",
    "ding.mp3",
    "explode.mp3",
    "shhha.mp3"
]

export class BabylonSounds {
    static loadSounds() {
        sounds.forEach(BabylonSounds.loadSound)
    }

    static loadSound(path) {
        path = SOUNDS_DIR + path
        const sound = new BABYLON.Sound(path, path, baby.scene)
        baby.soundData.soundMap.set(path, sound)
    }

    static playSound(key) {
        baby.soundData.soundMap.get(key).play()
    }

    static playPew() {
        BabylonSounds.playSound(SOUNDS_DIR + "pew.mp3")
    }

}