# build using maven wrapper
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

COPY src src

RUN ./mvnw clean package -DskipTests


# Run jar
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]