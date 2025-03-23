# Use uma imagem do JDK como base
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do container
WORKDIR /app

# Copie os arquivos do projeto para o container
COPY target/TechMesa-0.0.1-SNAPSHOT.jar /app/TechMesa-0.0.1-SNAPSHOT.jar

# Exponha a porta usada pelo aplicativo
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "TechMesa-0.0.1-SNAPSHOT.jar"]