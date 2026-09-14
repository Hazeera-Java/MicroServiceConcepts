FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/tracking-service.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]