
const game = {
    scores: []
}

game.tick = function () {
    // sphere.position.x += (0.05 * DIR.x)
    // sphere.position.y += (0.05 * DIR.y)
    // sphere.position.z += (0.05 * DIR.z)
    // camera.position.x = sphere.position.x
    // camera.position.z = sphere.position.z

}

game.changeView = function (view) {
    toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
    toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
    toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
    if (view == "VIEW_CANVAS") {
        baby.setup3D()
    }
}

game.toggleElement = function (id, toggle) {
    if (toggle) {
        document.getElementById(id).style.display = "block"
        document.getElementById(id).style.visibility = "visible"
    } else {
        document.getElementById(id).style.visibility = "hidden"
        document.getElementById(id).style.display = "none"
    }
}

game.appStart = function () {
    Web.toggleElement("VIEW_CANVAS", false)
    Web.toggleElement("VIEW_GAMES", false)

    const localHostname = window.location.hostname
    const port = window.location.port
    let s
    if (port == 80) {
        s = localHostname + "/"
    } else {
        s = localHostname + ":" + port + "/"
    }
    document.querySelector('#selectServer [value="' + s + '"]').selected = true

}

window.onload = game.appStart
// var t = setInterval(tick, 1000)
