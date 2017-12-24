docker stop gg
docker rm gg
docker rmi loc/gg:1
mvn clean install
docker build -t loc/gg:1 .
docker run --name gg --publish 8080:8080 loc/gg:1