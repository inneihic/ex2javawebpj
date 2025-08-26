FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY ex2javawebpj/main/pom.xml .
RUN mvn dependency:go-offline -B
COPY ex2javawebpj/main/src ./src
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
