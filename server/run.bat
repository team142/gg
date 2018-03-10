

cd ../client
call build.bat
cd ../server


echo src/main/resources/public

rmdir src/main/resources/public /s /q


del /F /Q src/main/resources/public
ROBOCOPY ../client/build/ src/main/resources/public /MIR /E

gradle clean bootRepackage

docker build -t team142/gg:local .
docker stop /gg
docker rm /gg
docker rmi /gg
docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev team142/gg:local
