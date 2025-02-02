FROM openjdk:17-jdk-alpine
# FROM gcr.io/distroless/java17-debian12
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/finops-automation-metadata-rest-server.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]

