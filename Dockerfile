FROM openjdk:11

COPY ./build/libs/* ./messagechat-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/m2", "-jar", "messagechat-0.0.1-SNAPSHOT.jar"]
