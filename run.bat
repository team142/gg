docker stop gg
docker rm gg
docker rmi team142/gg:local
mvn clean install
docker build -t team142/gg:local .
docker run --name gg --publish 8080:8080 --env REPORT_SERVER_STATS_AS=dev --env PUSHOVER_USER=u4xfyuftwprmf5zyazdrbx7cnhs2o1 --env PUSHOVER_TOKEN=aywmwjtuz5rpdopx6a66w3wjzibdon team142/gg:local

 
