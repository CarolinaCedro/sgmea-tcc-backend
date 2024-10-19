# Use a imagem oficial do MySQL
FROM mysql:latest

# Defina as variáveis de ambiente necessárias para o MySQL
ENV MYSQL_ROOT_PASSWORD=123456
ENV MYSQL_DATABASE=sgmea-db
