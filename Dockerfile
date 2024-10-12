# Use uma imagem base do Maven com Amazon Corretto 17
FROM maven:3.8.6-amazoncorretto-17 as build

# Diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o contêiner
COPY pom.xml .

# Baixa as dependências do Maven e as coloca em cache
RUN mvn dependency:go-offline -B

# Copia o restante dos arquivos para o contêiner
COPY src /app/src

# Limpa qualquer build anterior no diretório target
RUN rm -rf /app/target

# Compila o aplicativo e gera o arquivo JAR
RUN mvn clean package -DskipTests

# Use uma imagem base do Amazon Corretto 17 para a execução
FROM amazoncorretto:17-alpine

# Diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR gerado pela compilação para o contêiner
COPY --from=build /app/target/sgmea-backend-0.0.1-SNAPSHOT.jar /app/sgmea-backend-0.0.1-SNAPSHOT.jar

# Exponha a porta onde o aplicativo irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/sgmea-backend-0.0.1-SNAPSHOT.jar"]
