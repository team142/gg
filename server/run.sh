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
mkdir ${frontendFolder}
cp -rf ../client/build/* ${frontendFolder}
cp -rf src/main/WEB-INF ${frontendFolder}

gradle bootRun


#docker build -t team142/gg:local .
#docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev team142/gg:local
