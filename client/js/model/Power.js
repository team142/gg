import { TEXTURES_DIR, ICONS_DIR } from '../view/BabylonTextures.js'
import { BabylonUI } from '../view/BabylonUI.js'
import { PowerCooldownBar } from './PowerCooldownBar.js'


export const powerIconInfo = [
    { powerNumber: 1, level: 0, ico: ICONS_DIR + "ico-shoot.svg", cooldown: 20, usable: true, key: "1" },
    { powerNumber: 2, level: 0, ico: TEXTURES_DIR + "ico-tail2.png", cooldown: 20, usable: false, key: "2" },
    { powerNumber: 3, level: 0, ico: ICONS_DIR + "ico-missile.svg", cooldown: 20, usable: false, key: "3" },
    { powerNumber: 4, level: 0, ico: ICONS_DIR + "ico-seeker.svg", cooldown: 20, usable: false, key: "4" },
    { powerNumber: 5, level: 0, ico: ICONS_DIR + "ico-bomb.svg", cooldown: 20, usable: false, key: "5" },
    { powerNumber: 6, level: 0, ico: ICONS_DIR + "ico-radar.svg", cooldown: 20, usable: false, key: "6" },
    { powerNumber: 7, level: 0, ico: ICONS_DIR + "ico-intel.svg", cooldown: 20, usable: false, key: "7" },
    { powerNumber: 8, level: 0, ico: TEXTURES_DIR + "ico-safety2.png", cooldown: 20, usable: false, key: "8" },
    { powerNumber: 9, level: 0, ico: TEXTURES_DIR + "ico-behind2.png", cooldown: 20, usable: false, key: "9" },
    { powerNumber: 10, level: 0, ico: ICONS_DIR + "ico-hp-mech1.svg", cooldown: 20, usable: false, key: "0" }
]

export const passiveIconInfo = [
    { id: 1, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'Z', usable: false },
    { id: 2, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'X', usable: false },
    { id: 3, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'C', usable: false },
    { id: 4, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'V', usable: false },
    { id: 5, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'B', usable: false },
    { id: 6, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'N', usable: false },
    { id: 7, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: 'M', usable: false },
    { id: 8, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: ',', usable: false },
    { id: 9, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: '.', usable: false },
    { id: 10, level: 0, ico: TEXTURES_DIR + "ico-blank.jpg", key: '/', usable: false }
]
