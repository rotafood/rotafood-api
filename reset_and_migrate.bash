#!/bin/bash

# Variáveis de ambiente para o banco de dados
DATABASE_URL="postgresql://localhost:5432/rotafood_api_db"
DATABASE_USERNAME="postgres"
DATABASE_PASSWORD="postgres"

# Caminho para o arquivo SQL a ser excluído
DDL_FILE="./src/main/resources/db/migration/ddl_create_initial_schema.sql"

# Excluir o arquivo SQL se existir
if [ -f "$DDL_FILE" ]; then
    echo "Excluindo o arquivo $DDL_FILE..."
    rm "$DDL_FILE"
else
    echo "Arquivo $DDL_FILE não encontrado, prosseguindo..."
fi

# Extrair o nome do banco de dados da URL
DATABASE_NAME=$(echo $DATABASE_URL | awk -F '/' '{print $NF}')

# Executar os comandos no banco de dados
echo "Conectando ao banco de dados e manipulando o esquema public..."
psql "postgresql://$DATABASE_USERNAME:$DATABASE_PASSWORD@localhost:5432/$DATABASE_NAME" <<SQL
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO $DATABASE_USERNAME;
GRANT ALL ON SCHEMA public TO public;
SQL

if [ $? -ne 0 ]; then
    echo "Erro ao manipular o esquema public. Verifique a conexão com o banco de dados."
    exit 1
fi

# Rodar o Maven para iniciar o Spring Boot
echo "Iniciando o Spring Boot com Maven..."
mvn spring-boot:run &
MVN_PID=$!

# Aguardar o Spring Boot inicializar e gerar as migrações
sleep 10

# Parar o processo do Spring Boot
kill $MVN_PID

# Migrar os dados para o esquema public
echo "Aplicando migrações..."
psql "postgresql://$DATABASE_USERNAME:$DATABASE_PASSWORD@localhost:5432/$DATABASE_NAME" <<SQL
\\i ./src/main/resources/db/migration/*.sql
SQL

if [ $? -ne 0 ]; then
    echo "Erro ao rodar as migrações no esquema public."
    exit 1
fi

echo "Processo concluído com sucesso."
