FROM openjdk:21
WORKDIR /app
COPY /build/libs/AgroPro-0.0.1-SNAPSHOT.jar agropro-app.jar
RUN mkdir -p /app/logs
ENTRYPOINT ["java", "-jar", "agropro-app.jar"]