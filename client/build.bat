del build/
mkdir build
mkdir build/js/
robocopy index.html build
robocopy favicon.ico build
robocopy scene.babylon.manifest build
robocopy textures build
robocopy sounds build
robocopy js/babylon build/js
rollup js/App.js --o build/js/App.js --f es
