#!/bin/bash

set -e

echo "Verificando se algum container do RotaFood já está em execução..."

# Verifica se há containers com nome contendo 'rotafood'
if docker ps --format '{{.Names}}' | grep -q "rotafood"; then
  echo "Serviços do RotaFood já estão rodando. Nada será feito."
  exit 0
fi

# Se não há containers rotafood rodando, mas há outros containers ativos
if [ "$(docker ps -q)" ]; then
  echo "Parando todos os containers ativos..."
  docker stop $(docker ps -q)
  echo "Removendo todos os containers parados..."
  docker rm $(docker ps -aq)
fi

echo "Subindo os serviços do docker-compose do RotaFood..."
docker compose -f 'docker-compose.yaml' up -d --build

echo "Iniciando o Spring Boot com Maven (modo bloqueante)..."
mvn spring-boot:run
