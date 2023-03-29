FROM openjdk:17
ADD target/rmdemo.jar rmdemo.jar
ENTRYPOINT ["java", "-jar","rmdemo.jar"]    