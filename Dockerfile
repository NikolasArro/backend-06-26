# BUILD STAGE
FROM eclipse-temurin:21-jdk-alpine

RUN mvn clean package -DscipTests

# RUN STAGE
FROM eclipse-temurin:21-jdk-alpine

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]