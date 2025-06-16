FROM eclipse-temurin:21-jdk as build

WORKDIR /app

COPY . /app

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre

ARG PORT

ENV PORT=${PORT}

COPY --from=build /app/app.jar .

RUN useradd runtime
USER runtime

ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=${MYENV}", "-jar", "app.jar" ]

