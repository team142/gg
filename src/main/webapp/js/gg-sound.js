
const gSound = {
    soundMap: new Map()

}

class SoundUtils {
    static loadSounds() {
        SoundUtils.loadSound("sounds/pew.mp3")

    }

    static loadSound(path) {
        var sound = new BABYLON.Sound(path, path, baby.scene)
        gSound.soundMap.set(path, sound)

    }

    static playSound(key) {
        gSound.soundMap.get(key).play()

    }

    static playPew() {
        SoundUtils.playSound("sounds/pew.mp3")

    }

}
