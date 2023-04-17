FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/pruebann-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} prueba.jar
ENTRYPOINT ["java", "-jar", "/prueba.jar"]