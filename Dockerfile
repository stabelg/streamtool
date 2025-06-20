# Stage 1: Build do projeto com Gradle
FROM gradle:8.2.1-jdk17 AS build
WORKDIR /app

# Copia os arquivos de build
COPY build.gradle settings.gradle ./
COPY src ./src

# Gera o JAR (skip dos teste
RUN gradle clean build -x test

# Usar imagem base com JDK 21 Alpine (leve)
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta (ajuste conforme sua aplicação)
EXPOSE 8080

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]

