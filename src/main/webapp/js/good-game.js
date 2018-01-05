var game = {};

game.tick = function() {
    // sphere.position.x += (0.05 * DIR.x);
    // sphere.position.y += (0.05 * DIR.y);
    // sphere.position.z += (0.05 * DIR.z);
    // camera.position.x = sphere.position.x;
    // camera.position.z = sphere.position.z;

}

game.appStart = function() {
    web.toggleElement("VIEW_CANVAS", false)
    web.toggleElement("VIEW_GAMES", false)
    
}
window.onload = game.appStart;
