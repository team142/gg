
function createAndSaveMaterial(textureFilePath) {
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
