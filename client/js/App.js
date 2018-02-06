import { Web } from './view/Web.js'
import { Bullet } from './model/Bullet.js'

class App {

    static startApp() {

        Web.showStartScreen()
        Web.chooseServer()
        Web.assignJoinButton()
        Web.retrievePreviousName()

        Bullet.setupTicker()
    }

}

onload = App.startApp