FROM eclipse-temurin:22-jdk-ubi9-minimal
WORKDIR /app
COPY target/LaboFootApp-0.0.1.jar LaboFootApp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "LaboFootApp.jar"]
