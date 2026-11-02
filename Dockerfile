# Use Java 17 Runtime
FROM eclipse-temurin:17-jre

# Set Working Directory
WORKDIR /app

# Copy JAR file
COPY target/*.jar app.jar

# Expose Application Port
EXPOSE 8080

# Run Spring Boot Application
ENTRYPOINT ["java", "-jar", "app.jar"]