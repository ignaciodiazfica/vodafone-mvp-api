
# Base image for JDK 17
FROM openjdk:17

# Create working directory
WORKDIR /app

# Copy the jar file to container
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "app.jar"]