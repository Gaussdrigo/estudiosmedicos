# syntax=docker/dockerfile:1

# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom first to maximize Docker layer cache for dependencies
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy source and build the executable jar
COPY src ./src
RUN mvn -q -DskipTests clean package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Render injects PORT; this is only a default for local container runs
ENV PORT=8080
EXPOSE 8080

COPY --from=builder /app/target/estudiosmedicos-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
