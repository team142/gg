var gSound = {
    soundMap: new Map()

}

gSound.loadSounds = function () {
    var i = 0
    gSound.loadSound(i, "sounds/pew.mp3")

}

gSound.loadSound = function (name, path) {
    var sound = new BABYLON.Sound("sound" + name, path, scene)
    gSound.soundMap.set(path, sound)

}

gSound.playSound = function (key) {
    gSound.soundMap.get(key).play()

}

gSound.playPew = function () {
    gSound.playSound("sounds/pew.mp3")

}


