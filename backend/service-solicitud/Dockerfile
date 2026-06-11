# ETAPA 1: Construcción (Compila el código con Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Levanta el servidor Spring Boot)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto de tu API
EXPOSE 4419
# Comando para arrancar
ENTRYPOINT ["java", "-jar", "app.jar"]