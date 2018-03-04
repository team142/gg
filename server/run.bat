cd ../client
call build.bat
cd ../server
ROBOCOPY ../client/build/ src/main/webapp /MIR
docker stop gg
docker rm gg
docker rmi team142/gg:local
mvn clean install
docker build -t team142/gg:local .

rmdir src\main\webapp /s /q

docker run --name gg --publish 8080:8080 team142/gg:local

 
