# Use a slim Java base image with JDK 17
FROM openjdk:17.0.2-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/gradle-ddd-girokonto-1.0-SNAPSHOT.jar gradle-ddd-girokonto-app.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "gradle-ddd-girokonto-app.jar"]