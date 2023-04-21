FROM eclipse-temurin:19-jre-alpine

EXPOSE 8080

RUN addgroup app && adduser -G app -D app

USER app

COPY target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]