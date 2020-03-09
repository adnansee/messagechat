FROM openjdk:13-jdk-alpine
ARG JAR_FILE=./build/libs/messagechat-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./build/libs/
ENTRYPOINT ["java","-jar","/build/libs/messagechat-0.0.1-SNAPSHOT.jar"]