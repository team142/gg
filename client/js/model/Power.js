import { TEXTURES_DIR } from '../view/BabylonTextures.js'
import { BabylonUI } from '../view/BabylonUI.js'
import { PowerCooldownBar } from './PowerCooldownBar.js'


export const powerIconInfo = [
    { powerNumber: 1, level: 0, ico: TEXTURES_DIR + "ico-shoot.jpg", cooldown: 20, usable: true },
    { powerNumber: 2, level: 0, ico: TEXTURES_DIR + "ico-tail.jpg", cooldown: 20, usable: false },
    { powerNumber: 3, level: 0, ico: TEXTURES_DIR + "ico-missile.jpg", cooldown: 20, usable: false },
    { powerNumber: 4, level: 0, ico: TEXTURES_DIR + "ico-seeker.jpg", cooldown: 20, usable: false },
    { powerNumber: 5, level: 0, ico: TEXTURES_DIR + "ico-bomb.jpg", cooldown: 20, usable: false },
    { powerNumber: 6, level: 0, ico: TEXTURES_DIR + "ico-radar.jpg", cooldown: 20, usable: false },
    { powerNumber: 7, level: 0, ico: TEXTURES_DIR + "ico-intel.jpg", cooldown: 20, usable: false },
    { powerNumber: 8, level: 0, ico: TEXTURES_DIR + "ico-safety.jpg", cooldown: 20, usable: false },
    { powerNumber: 9, level: 0, ico: TEXTURES_DIR + "ico-behind.jpg", cooldown: 20, usable: false },
    { powerNumber: 10, level: 0, ico: TEXTURES_DIR + "ico-hp-mech.jpg", cooldown: 20, usable: false }
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
