FROM openjdk:21-jdk-slim
LABEL authors="uzay"
EXPOSE 8080
COPY target/oauthuygulama-0.0.1-SNAPSHOT.jar oauthuygulama-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","oauthuygulama-0.0.1-SNAPSHOT.jar"]