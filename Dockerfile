FROM openjdk:8u131-jdk-alpine
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER Cloud1 "cloud1@dal.ca"

EXPOSE 8000

WORKDIR .
COPY ./ ./
RUN mvn -f ./pom.xml clean install
# COPY ./target/hiree-1.0-SNAPSHOT.jar hiree-1.0-SNAPSHOT.jar

CMD ["java","-jar","./target/hiree-1.0-SNAPSHOT.jar"]