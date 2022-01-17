#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

echo "Running PetClinic microservices"
nohup java -jar spring-petclinic-microservices/spring-petclinic-customers-service/target/*.jar --server.port=8081 > logs/customers-service.log 2>&1 &
nohup java -jar spring-petclinic-microservices/spring-petclinic-visits-service/target/*.jar --server.port=8082 > logs/visits-service.log 2>&1 &
nohup java -jar spring-petclinic-microservices/spring-petclinic-vets-service/target/*.jar --server.port=8083 > logs/vets-service.log 2>&1 &
echo "Waiting for microservices to start"
wait-for-it --service localhost:8081 --service localhost:8082 --service localhost:8083 --timeout 60 --parallel