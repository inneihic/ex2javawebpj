# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY main/pom.xml .
RUN mvn dependency:go-offline -B
COPY main/src ./src
RUN mvn clean package -DskipTests -B

# Run stage - use lightweight JRE image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy jar produced by maven (assumes spring-boot executable jar)
COPY --from=build /app/target/*.jar app.jar

# Make sure the app listens on the port Render provides via $PORT.
# Spring Boot reads server.port from the system property or environment variable SERVER_PORT.
ENV PORT=8080
ENV SERVER_PORT=$PORT

# Expose default port (optional)
EXPOSE 8080

# Start application and bind to $PORT (fallback to 8080)
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
