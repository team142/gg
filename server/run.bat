cd ../client
build.bat
cd ../server
docker stop gg
docker rm gg
docker rmi team142/gg:local
mvn clean install
docker build -t team142/gg:local .
docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev team142/gg:local

 
