FROM openjdk:17-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-Xmx500M", "-Xms500M", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]