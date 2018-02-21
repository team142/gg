#!/bin/bash
rm -rf build/
mkdir build
mkdir build/js/
cp index.html build
cp favicon.ico build
cp scene.babylon.manifest build
cp -rf textures build
cp -rf sounds build
cp -rf js/babylon build/js
rollup js/App.js --o build/js/App.js --f es
uglifyjs --compress -o js/App.js  --mangle -- js/App.js