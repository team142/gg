
const baby = {
    materialsMap: new Map(),
    DIR: {
        x: 0,
        y: 0,
        z: 0
    },
    textScores: [],
    counter: 0

}

class BabylonUtils {

    static getCounter() {
        baby.counter++
        return baby.counter

    }

    static setup3D() {
        baby.canvas = document.getElementById("VIEW_CANVAS")
        baby.engine = new BABYLON.Engine(baby.canvas, true)
        BabylonUtils.createScene()
        baby.engine.runRenderLoop(function () {
            baby.scene.render()
        })
        window.addEventListener("resize", function () {
            baby.engine.resize()
        })
        window.addEventListener("keyup", function (data) {
            ServerIO.sendKeyUp(data.key)
            const key = data.key
            if (key === "a" || key === "A") {
                baby.DIR.x = 0
            } else if (key === "d" || key === "D") {
                baby.DIR.x = 0
            } else if (key === "s" || key === "S") {
                baby.DIR.z = 0
            } else if (key === "w" || key === "W") {
                baby.DIR.z = 0
            }

        })
        window.addEventListener("keydown", function (data) {
            ServerIO.sendKeyDown(data.key)
            const key = data.key
            if (key === "a" || key === "A") {
                baby.DIR.x = -1
            } else if (key === "d" || key === "D") {
                baby.DIR.x = 1
            } else if (key === "s" || key === "S") {
                baby.DIR.z = -1
            } else if (key === "w" || key === "W") {
                baby.DIR.z = 1
            }
        })
        BabylonUtils.createBaseTile()
        BabylonUtils.createMountainTile()
        SoundUtils.loadSounds()
        BabylonUtils.createSkyBox()
        baby.baseBullet = BabylonUtils.createBaseBullet()
        // var t = setInterval(movementTick, 40)

    }

    static createSkyBox() {
        // Skyboxes
        const skyboxMaterial = new BABYLON.StandardMaterial("skyBox", baby.scene)
        skyboxMaterial.backFaceCulling = false
        skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("textures/skybox", baby.scene)
        skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE
        skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0)
        skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0)
        skyboxMaterial.disableLighting = true
        const skybox1 = BABYLON.Mesh.CreateBox("skyBox1", 50 * 50, baby.scene)
        skybox1.material = skyboxMaterial
        skybox1.visibility = 1

    }

    static displayScores() {
        baby.textScores.forEach((ro) => {
            ro.dispose()
        })
        game.scores.forEach((row, i) => {
            BabylonUtils.createRightText(i, row.key, row.value)
        })

    }

    static createRightText(num, name, score) {
        const current = BABYLON.GUI.Button.CreateSimpleButton("but" + BabylonUtils.getCounter(), name + ": " + score)
        current.width = 1
        current.height = "50px"
        current.color = "green"
        current.background = "white"
        baby.panelScores.addControl(current)
        baby.textScores.push(current)

    }

    static createSphereIfNotExists(tagId, labelText) {
        if (tagId) {

            //Ignore players I know of
            const result = Match.getPlayerByTag(tagId)
            if (result) {
                return
            }

            ////////////////////////////////////////////////////////////////
            ////////////    *** Comes from Tank model project *** //////////
            ////////////////////////////////////////////////////////////////

            const name = "player" + match.gameInstance + tagId

            const box = BABYLON.MeshBuilder.CreateBox("box" + name, {height: 0.3, width: 0.6, depth: 0.3}, baby.scene);
            box.position.y = 0.0
            box.material = baby.matGrey
            
            const boxBarrel = BABYLON.MeshBuilder.CreateBox("boxBarrel" + name, {height: 0.05, width: 0.6, depth: 0.05}, baby.scene);
            boxBarrel.position.y = 0.1
            boxBarrel.position.x = 0.3
            boxBarrel.material = baby.matBlack
    
            const boxLeftWing = BABYLON.MeshBuilder.CreateBox("boxLeftWing" + name, {height: 0.1, width: 0.65, depth: 0.05}, baby.scene);
            boxLeftWing.position.y = -0.11
            boxLeftWing.position.z = 0.15
            boxLeftWing.material = baby.matWing
    
    
            var boxRightWing = BABYLON.MeshBuilder.CreateBox("boxRightWing" + name, {height: 0.1, width: 0.65, depth: 0.05}, baby.scene);
            boxRightWing.position.y = -0.11
            boxRightWing.position.z = -0.15
            boxRightWing.material = baby.matWing
    
            box.addChild(boxBarrel)
            box.addChild(boxLeftWing)
            box.addChild(boxRightWing)

            ////////////////////////////////////////////////////////////////////////////////////

            const item = box
            match.playerTanks.set(tagId, item)

            const rectText = new BABYLON.GUI.Rectangle()
            rectText.width = 0.2
            rectText.height = "100px"
            rectText.cornerRadius = 20
            rectText.thickness = 0
            baby.advancedTexture.addControl(rectText)
            match.playerRectangles.set(tagId, rectText)

            const label = new BABYLON.GUI.TextBlock()
            label.text = labelText
            match.playerLabels.set(tagId, label)

            rectText.addControl(label)
            rectText.linkWithMesh(item)
            rectText.linkOffsetY = -50

        }

    }

    static createMaterials() {
        const textureFiles = [
            "grass1-min.jpg",
            "grass2-min.jpg",
            "grass3-min.jpg",
            "rock1-min.jpg",
            "rock2-min.jpg",
            "rock3-min.jpg",
            "water1-min.jpg",
            "water2-min.jpg",
            "water3-min.jpg"
        ]
        for (const file of textureFiles) {
            BabylonUtils.createAndSaveMaterial("/textures/" + file)
        }

        //For now create a smiley-face for each person
        baby.smileyMaterial = new BABYLON.StandardMaterial("", baby.scene)
        baby.smileyMaterial.diffuseTexture = new BABYLON.Texture("textures/smily.png", baby.scene)

        baby.matGrey = new BABYLON.StandardMaterial("matGrey", baby.scene);
        baby.matGrey.diffuseColor = new BABYLON.Color3(0.5, 0.5, 0.5);

        baby.matBlack = new BABYLON.StandardMaterial("matBlack", baby.scene);
        baby.matBlack.diffuseColor = new BABYLON.Color3(0, 0, 0);

        baby.matWing = new BABYLON.StandardMaterial("matWing", baby.scene);
        baby.matWing.diffuseColor = new BABYLON.Color3(0.2, 0.2, 1);



    }


    static createAndSaveMaterial(textureFilePath) {
        const materialPlane = new BABYLON.StandardMaterial("texturePlane", baby.scene)
        materialPlane.diffuseTexture = new BABYLON.Texture(textureFilePath, baby.scene)
        materialPlane.diffuseTexture.uScale = 1.0//Repeat 5 times on the Vertical Axes
        materialPlane.diffuseTexture.vScale = 1.0//Repeat 5 times on the Horizontal Axes
        materialPlane.backFaceCulling = false//Always show the front and the back of an element
        baby.materialsMap.set(textureFilePath, materialPlane)

    }

    static createMap(arr) {
        for (const t of arr) {
            BabylonUtils.createMapTile(t.x, t.z, t.skin, t.model)
        }

    }

    static createBaseBullet() {
        const cone = BABYLON.MeshBuilder.CreateCylinder("cone", { diameterTop: 0, height: 1, tessellation: 96 }, baby.scene)
        const scl = 0.0625 //Much smaller than normal cone
        const scalingFactor = new BABYLON.Vector3(scl, scl, scl)
        cone.scaling = scalingFactor
        cone.position.multiplyInPlace(scalingFactor)
        cone.rotation.x = -1.57
        return cone

    }

    static createBullet(obj) {
        const b = new Bullet(obj.BULLET, baby.baseBullet.clone("bullet" + BabylonUtils.getCounter()))
        bullets.push(b)

    }

    static createMapTile(x, y, skin, model) {
        let plane;
        if (model == "FLAT_TILE") {
            plane = baby.baseTile.clone(("plane" + x) + y)
        } else if (model == "ROCK_TILE") {
            plane = baby.mountainTile.clone(("plane" + x) + y)
        }
        plane.position.z = (y * 1)
        plane.position.x = (x * 1)
        plane.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            plane.material = material
        }
        plane.visibility = true

    }

    static createBaseTile() {
        const x = -1
        const y = -1
        const skin = "/textures/water1-min.jpg"
        baby.baseTile = BABYLON.Mesh.CreatePlane(("plane" + x) + y, 1, baby.scene)
        baby.baseTile.position.z = (y * 1)
        baby.baseTile.position.x = (x * 1)
        baby.baseTile.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            baby.baseTile.material = material;
        }
        baby.baseTile.visibility = false

    }

    static createMountainTile() {
        const x = -2
        const y = -2
        const skin = "/textures/rock1-min.jpg"

        baby.mountainTile = BABYLON.MeshBuilder.CreateBox(("plane" + x) + y, { height: 1, width: 1, depth: 1 }, baby.scene);
        baby.mountainTile.position.z = (y * 1)
        baby.mountainTile.position.x = (x * 1)
        baby.mountainTile.position.y = 0.5
        baby.mountainTile.rotation.x = Math.PI / 2
        const material = baby.materialsMap.get(skin)
        if (material) {
            baby.mountainTile.material = material;
        }
        baby.mountainTile.visibility = false

    }

    static createGui() {
        baby.advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI")
        baby.panelScores = new BABYLON.GUI.StackPanel()
        baby.panelScores.width = "220px"
        baby.panelScores.height = "200px"
        baby.panelScores.fontSize = "14px"
        baby.panelScores.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT
        baby.panelScores.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_CENTER
        baby.advancedTexture.addControl(baby.panelScores)

    }

    static createSpray(tankId, ms) {

        const tank = Match.getPlayerByTag(tankId)

        // Create a particle system
        const particleSystem = new BABYLON.ParticleSystem("particles", 2000, baby.scene);

        //Texture of each particle
        particleSystem.particleTexture = new BABYLON.Texture("textures/flare.png", baby.scene);

        // Where the particles come from
        particleSystem.emitter = tank; // the starting object, the emitter
        particleSystem.minEmitBox = new BABYLON.Vector3(-1, 0, 0); // Starting all from
        particleSystem.maxEmitBox = new BABYLON.Vector3(1, 0, 0); // To...

        // Colors of all particles
        particleSystem.color1 = new BABYLON.Color4(0.7, 0.8, 1.0, 1.0);
        particleSystem.color2 = new BABYLON.Color4(0.2, 0.5, 1.0, 1.0);
        particleSystem.colorDead = new BABYLON.Color4(0, 0, 0.2, 0.0);

        // Size of each particle (random between...
        particleSystem.minSize = 0.1;
        particleSystem.maxSize = 0.5;

        // Life time of each particle (random between...
        particleSystem.minLifeTime = 0.3;
        particleSystem.maxLifeTime = 1.5;

        // Emission rate
        particleSystem.emitRate = 1500;

        // Blend mode : BLENDMODE_ONEONE, or BLENDMODE_STANDARD
        particleSystem.blendMode = BABYLON.ParticleSystem.BLENDMODE_ONEONE;

        // Set the gravity of all particles
        particleSystem.gravity = new BABYLON.Vector3(0, -9.81, 0);

        // Direction of each particle after it has been emitted
        particleSystem.direction1 = new BABYLON.Vector3(-7, 8, 3);
        particleSystem.direction2 = new BABYLON.Vector3(7, 8, -3);

        // Angular speed, in radians
        particleSystem.minAngularSpeed = 0;
        particleSystem.maxAngularSpeed = Math.PI;

        // Speed
        particleSystem.minEmitPower = 1;
        particleSystem.maxEmitPower = 3;
        particleSystem.updateSpeed = 0.005;

        var updateFunction = function (particles) {
            for (var index = 0; index < particles.length; index++) {
                var particle = particles[index];
                particle.age += this._scaledUpdateSpeed;

                // change direction to return to emitter
                if (particle.age >= particle.lifeTime / 2) {
                    var oldLength = particle.direction.length();
                    var newDirection = this.emitter.position.subtract(particle.position);
                    particle.direction = newDirection.scale(3);
                }

                if (particle.age >= particle.lifeTime) { // Recycle
                    particles.splice(index, 1);
                    this._stockParticles.push(particle);
                    index--;
                    continue;

                } else {
                    particle.colorStep.scaleToRef(this._scaledUpdateSpeed, this._scaledColorStep);
                    particle.color.addInPlace(this._scaledColorStep);

                    if (particle.color.a < 0)
                        particle.color.a = 0;

                    particle.angle += particle.angularSpeed * this._scaledUpdateSpeed;

                    particle.direction.scaleToRef(this._scaledUpdateSpeed, this._scaledDirection);
                    particle.position.addInPlace(this._scaledDirection);

                    this.gravity.scaleToRef(this._scaledUpdateSpeed, this._scaledGravity);
                    particle.direction.addInPlace(this._scaledGravity);
                }
            }

        }

        particleSystem.updateFunction = updateFunction;
        particleSystem.start();
        function stop() {
            particleSystem.stop()
        }
        setTimeout(stop, ms);


    }

    static createScene() {
        baby.scene = new BABYLON.Scene(baby.engine)
        baby.scene.name = "scene"
        baby.camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 0, -15), baby.scene)
        baby.camera.setTarget(BABYLON.Vector3.Zero())
        baby.camera.position.y = 0.75
        const light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), baby.scene)
        light.intensity = 0.9
        BabylonUtils.createMaterials()
        BabylonUtils.createGui()

    }

}