#!/bin/bash

# Passo 1: Faz o pull da branch master
echo "Fazendo git pull na branch master..."
git pull origin master

# Passo 2: Compila o projeto (certifique-se de ter o Maven ou Gradle instalado)
echo "Compilando o projeto..."
mvn clean install  # ou `./gradlew build` se estiver usando Gradle

# Passo 3: Executa a aplicação Spring Boot com limite de memória e profile prod
echo "Iniciando a aplicação com limite de memória e profile 'prod'..."
sudo java -Xms256m -Xmx512m -jar target/sgmea-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# Fim do script
echo "Aplicação iniciada com sucesso no profile 'prod'!"
