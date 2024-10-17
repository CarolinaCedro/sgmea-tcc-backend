echo "Fazendo git pull na branch master..."
git pull origin master

# Passo 3: Compila o projeto (certifique-se de ter o Maven ou Gradle instalado)
echo "Compilando o projeto..."
mvn clean install  # ou `./gradlew build` se estiver usando Gradle

# Passo 4: Executa a aplicação Spring Boot com limite de memória
echo "Iniciando a aplicação com limite de memória..."
java -Xms256m -Xmx512m -jar target/sgmea-backend-0.0.1-SNAPSHOT.jar

# Fim do script
echo "Aplicação iniciada com sucesso!"