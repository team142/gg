var gSound = {
    sounds: []
}

gSound.loadSounds = function () {
    var i = 0
    gSound.loadSound(i, "sounds/pew.mp3")

}

gSound.loadSound = function (name, path) {
    var sound = new BABYLON.Sound("sound" + name, path, scene)
    var item = {
        key: path,
        value: sound
    }
    gSound.sounds.push(item)

}

gSound.playSound = function (key) {
    var snd = gSound.sounds.find(function (item) {
        return item.key == key
    })
    if (snd) {
        snd.value.play()
    }

}

gSound.playPew = function () {
    gSound.playSound("sounds/pew.mp3")

}


