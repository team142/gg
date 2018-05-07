import {Web} from './view/Web.js'

class App {
    static startApp() {
        Web.showStartScreen()
        Web.chooseServer()
        Web.assignJoinButton()
        Web.retrievePreviousName()

    }
}

onload = App.startApp