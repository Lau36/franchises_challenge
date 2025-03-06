
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/franquicias_prueba-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENV DATABASE_URL=r2dbc:mysql://database-franchises.cot4aa2qs92t.us-east-1.rds.amazonaws.com:3306/franchises
ENV DATABASE_USER_NAME=admin
ENV DATABASE_PASSWORD=admin1

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t franchises-api .
#docker run -p 9090:9090 franchises-api