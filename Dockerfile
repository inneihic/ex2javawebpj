# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml trước để cache dependencies
COPY ex2javawebpj/main/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code và build
COPY ex2javawebpj/main/src ./src
RUN mvn clean package spring-boot:repackage -DskipTests -B

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy jar đã build từ stage trước
COPY --from=build /app/target/*.jar app.jar

# Render sẽ set biến PORT, Spring Boot cần nghe đúng port đó
ENV PORT=8080
EXPOSE 8080

# Chạy app, lắng nghe $PORT
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
