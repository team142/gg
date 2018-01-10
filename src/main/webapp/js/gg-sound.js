
var gSound = {
    soundMap: new Map()

}

gSound.loadSounds = function () {
    gSound.loadSound("sounds/pew.mp3")

}

gSound.loadSound = function (path) {
    var sound = new BABYLON.Sound(path, path, baby.scene)
    gSound.soundMap.set(path, sound)

}

gSound.playSound = function (key) {
    gSound.soundMap.get(key).play()

}

gSound.playPew = function () {
    gSound.playSound("sounds/pew.mp3")

}


