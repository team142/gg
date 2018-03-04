FROM openjdk:8-alpine 
COPY ./build/libs/gg-server-0.1.0.jar /usr/src/myapp/gg-server-0.1.0.jar
WORKDIR /usr/src/myapp
RUN java -jar gg-server-0.1.0.jar