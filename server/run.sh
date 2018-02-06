#!/bin/bash

docker stop gg
docker rm gg
docker rmi team142/gg:local

cd ../client
./build.sh
cd ../gg
mkdir src/main/webapp
cp -rf ../client/build/ src/main/webapp/
cp -rf src/main/WEB-INF src/main/webapp

mvn clean install

rm -rf src/main/webapp

docker build -t team142/gg:local .
docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev team142/gg:local
