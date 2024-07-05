# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port your application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/RegisterApp-0.0.1-SNAPSHOT.jar"]


