# BUILD STAGE
FROM eclipse-temurin:21-jdk-alpine AS build

RUN mvn clean package -DscipTests

# RUN STAGE
FROM eclipse-temurin:21-jdk-alpine

COPY --from=build target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]