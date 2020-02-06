FROM ubuntu:18.04

COPY ./build/libs/* ./messagechat-0.0.1-SNAPSHOT.jar

RUN apt-get update && \
    apt-get install -y curl \
    wget \
    openjdk-11-jdk

CMD ["java", "-jar", "messagechat-0.0.1-SNAPSHOT.jar"]
