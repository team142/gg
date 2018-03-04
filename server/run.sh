#!/bin/bash

#docker stop gg
#docker rm gg
#docker rmi team142/gg:local

cd ../client
./build.sh
cd ../server

frontendFolder=src/main/resources/public

echo ${frontendFolder}

rm -rf ${frontendFolder}
mkdir -p ${frontendFolder}
cp -rf ../client/build/* ${frontendFolder}
cp -rf src/main/WEB-INF ${frontendFolder}

gradle bootRepackage
java -jar ./build/libs/gs-messaging-stomp-websocket-0.1.0.jar


#docker build -t team142/gg:local .
#docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev team142/gg:local
