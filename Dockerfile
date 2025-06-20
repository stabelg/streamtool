# Usar imagem base com JDK 21 Alpine (leve)
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o jar gerado para dentro do container
# Ajuste o nome do jar caso seja diferente
COPY build/libs/streamtool-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta padrão que seu app usa
EXPOSE 8080

# Comando para rodar o Spring Boot fat jar
ENTRYPOINT ["java", "-jar", "app.jar"]
