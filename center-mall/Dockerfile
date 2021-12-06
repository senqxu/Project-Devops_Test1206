# the first stage of our build will extract the layers
FROM adoptopenjdk/openjdk11:alpine as builder
WORKDIR center-mall
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# the second stage of our build will copy the extracted layers
