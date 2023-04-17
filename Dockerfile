FROM openjdk:11-jre-slim
MAINTAINER prueba.dev <prueba.dev>
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/pruebann-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]