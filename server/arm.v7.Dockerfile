FROM arm32v7/openjdk:9.0.1-11-jre-slim
COPY ./build/libs/gg-server-0.1.0.jar /usr/src/myapp/gg-server-0.1.0.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "gg-server-0.1.0.jar"]
