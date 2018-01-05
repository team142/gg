var bUtils = {};



bUtils.createSphereIfNotExists = function (tagId) {
    if (tagId) {
        var result = getPlayerByTag(tagId);
        if (!result) {
            var name = "player" + gameInstance;
            name = name + tagId;
            var item = BABYLON.Mesh.CreateSphere(name, 16, 0.5, scene);
            item.position.y = 1;

            var mapItem = {
                key: tagId,
                value: item
            }

            playerTanks.push(mapItem);

        }
    }
}



bUtils.createMaterials = function () {
    //Grass
    bUtils.createAndSaveMaterial("/textures/grass1.jpg");
    bUtils.createAndSaveMaterial("/textures/grass2.jpg");
    bUtils.createAndSaveMaterial("/textures/grass3.jpg");
    bUtils.createAndSaveMaterial("/textures/rock1.jpg");
    bUtils.createAndSaveMaterial("/textures/rock2.jpg");
    bUtils.createAndSaveMaterial("/textures/rock3.jpg");
    bUtils.createAndSaveMaterial("/textures/water1.jpg");
    bUtils.createAndSaveMaterial("/textures/water2.jpg");
    bUtils.createAndSaveMaterial("/textures/water3.jpg");

}


bUtils.createAndSaveMaterial = function(textureFilePath) {
    var materialPlane = new BABYLON.StandardMaterial("texturePlane", scene);
    materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, scene);
    materialPlane.diffuseTexture.uScale = 1.0;//Repeat 5 times on the Vertical Axes
    materialPlane.diffuseTexture.vScale = 1.0;//Repeat 5 times on the Horizontal Axes
    materialPlane.backFaceCulling = false;//Always show the front and the back of an element
    var item = {
        key: textureFilePath,
        value: materialPlane
    };
    materials.push(item);
}


bUtils.createMapTile = function(x, y, skin) {
    var plane = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, scene);
    plane.position.z = (y * 1);
    plane.position.x = (x * 1);
    plane.rotation.x = Math.PI / 2;
    materials.forEach(function (entry) {
        if (entry.key == skin) {
            plane.material = entry.value;
        }
    });
}



bUtils.createGui = function() {
    // GUI
    var advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");

    var button1 = BABYLON.GUI.Button.CreateSimpleButton("but1", "Airstrike");
    button1.width = "125px"
    button1.height = "40px";
    button1.color = "white";
    button1.cornerRadius = 20;
    button1.background = "green";
    button1.top = "0px";
    button1.left = "0px";
    button1.onPointerUpObservable.add(function () {
        sphere.position.y = sphere.position.y + 0.1
        // alert("you did it!");
    });
    // advancedTexture.addControl(button1);

    var button2 = BABYLON.GUI.Button.CreateSimpleButton("but2", "Radar");
    button2.width = "125px"
    button2.height = "40px";
    button2.color = "white";
    button2.cornerRadius = 20;
    button2.background = "green";
    button2.top = "5px";
    button2.left = "0px";
    button2.onPointerUpObservable.add(function () {

        sphere.position.y = sphere.position.y - 0.1
        // alert("you did it!");
    });

    var panel3 = new BABYLON.GUI.StackPanel();
    panel3.width = "220px";
    panel3.fontSize = "14px";
    panel3.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT;
    panel3.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER;
    advancedTexture.addControl(panel3);
    panel3.addControl(button1);
    panel3.addControl(button2);


}



var createScene = function () {
    
        var scene = new BABYLON.Scene(engine);
        scene.name = "scene";
    
        camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), scene);
        camera.setTarget(BABYLON.Vector3.Zero());
        camera.position.y = 0.5;
    
        var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);
        light.intensity = 0.7;
    
    
        bUtils.createMaterials();
        bUtils.createGui();
    
    
        return scene;
    
    };
    