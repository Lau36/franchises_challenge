FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/franquicias_prueba-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENV DATABASE_URL=r2dbc:mysql://db-mysql:3306/franchises
ENV DATABASE_USER_NAME=root
ENV DATABASE_PASSWORD=admin

ENTRYPOINT ["java", "-jar", "app.jar"]