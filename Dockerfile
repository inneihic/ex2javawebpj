# Use an official OpenJDK base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Copy the Maven wrapper (if you use it, which your repo includes)
COPY mvnw .
COPY .mvn ./.mvn

# Ensure the Maven wrapper is executable
RUN chmod +x mvnw

# Install Maven and build the application, skipping tests to speed up the build
RUN ./mvnw clean package -DskipTests

# Expose the port that Render.com expects (default is 10000)
EXPOSE 10000

# Set the environment variable for the port (Render.com expects PORT)
ENV PORT=10000

# Run the Spring Boot application
CMD ["java", "-jar", "target/ex2javawebpj-0.0.1-SNAPSHOT.jar", "--server.port=${PORT}"]
