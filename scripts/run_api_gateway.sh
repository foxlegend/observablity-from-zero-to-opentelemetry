#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

echo "Running API gateway"
nohup java -jar spring-petclinic-microservices/spring-petclinic-api-gateway/target/*.jar --server.port=8080 > logs/gateway-service.log 2>&1 &
echo "Waiting for API gateway to start"
wait-for-it --service localhost:8080 --timeout 30