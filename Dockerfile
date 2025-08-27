# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/target/ex02javawebpj-1.0.0.jar app.jar

# Expose the port (Render.com defaults to 10000, but can be customized)
EXPOSE 8080

# Set environment variable for CSV file path (optional, for flexibility)
ENV CSV_STORAGE_PATH=/app/submissions.csv

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
