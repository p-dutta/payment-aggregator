FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests


FROM openjdk:17-jdk-alpine
WORKDIR /app
ENV PORT 8080
EXPOSE 8080
COPY --from=build /app/target/*SNAPSHOT.jar /app/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
