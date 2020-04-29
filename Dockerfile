FROM maven:3.6.0-jdk-11-slim AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean install