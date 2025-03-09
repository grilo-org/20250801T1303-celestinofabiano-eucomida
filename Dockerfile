# Usa a imagem oficial do OpenJDK 21
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR para dentro do contêiner
COPY target/eucomida-*.jar app.jar

# Expõe a porta 8098
EXPOSE 8098

# Comando para rodar a aplicação dentro do contêiner
ENTRYPOINT ["java", "-jar", "app.jar"]
