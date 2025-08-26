# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# copy toàn bộ project vào
COPY . .

# build với spring-boot:repackage để tạo runnable jar
RUN mvn clean package spring-boot:repackage -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app

# copy file jar từ stage build
COPY --from=build /app/target/*.jar app.jar

# expose port (ví dụ 8080)
EXPOSE 8080

# chạy app
ENTRYPOINT ["java","-jar","app.jar"]

