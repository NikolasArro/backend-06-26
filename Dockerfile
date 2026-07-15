# ==========================================
# 1. BUILD STAGE
# ==========================================
# Use a Maven image that already has JDK 21 and Maven installed
FROM maven:eclipse-temurin:21-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application and package it into a JAR
RUN mvn clean package -DskipTests

# ==========================================
# 2. RUN STAGE
# ==========================================
# Use a lighter JRE image to run the application
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose your application's port (adjust if your port is different, e.g., 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]