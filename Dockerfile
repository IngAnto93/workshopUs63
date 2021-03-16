FROM maven:3.5-jdk-8-alpine as builder
WORKDIR /workshop-us63
COPY pom.xml ./
COPY src ./src/
RUN mvn package
FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim
COPY --from=builder /workshop-us63/target/*.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=8080","-jar","app.jar"]
