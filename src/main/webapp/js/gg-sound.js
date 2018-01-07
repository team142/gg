var GSound = {
    sounds: []
};

GSound.loadSounds = function () {
    var i = 0;
    GSound.loadSound(i, "sounds/pew.mp3");

}

GSound.loadSound = function (name, path) {
    var sound = new BABYLON.Sound("sound" + name, path, scene);
    var item = {
        key: path,
        value: sound
    }
    GSound.sounds.push(item);

}

GSound.playSound = function (key) {
    var snd = GSound.sounds.find(function (item) {
        return item.key == key;
    });
    if (snd) {
        snd.value.play();
    }

}


