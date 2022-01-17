#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail


echo "Running apps"
nohup java -jar spring-petclinic-microservices/spring-petclinic-config-server/target/*.jar --server.port=8888 > logs/config-server.log 2>&1 &
echo "Waiting for config server to start"
wait-for-it --service localhost:8888 --timeout 30
nohup java -jar spring-petclinic-microservices/spring-petclinic-discovery-server/target/*.jar --server.port=8761 > logs/discovery-server.log 2>&1 &
echo "Waiting for discovery server to start"
wait-for-it --service localhost:8761 --timeout 30

echo "Running PetClinic microservices"
nohup java -jar spring-petclinic-microservices/spring-petclinic-customers-service/target/*.jar --server.port=8081 > logs/customers-service.log 2>&1 &
nohup java -jar spring-petclinic-microservices/spring-petclinic-visits-service/target/*.jar --server.port=8082 > logs/visits-service.log 2>&1 &
nohup java -jar spring-petclinic-microservices/spring-petclinic-vets-service/target/*.jar --server.port=8083 > logs/vets-service.log 2>&1 &
echo "Waiting for microservices to start"
wait-for-it --service localhost:8081 --service localhost:8082 --service localhost:8083 --timeout 60 --parallel

echo "Running Api gateway"
nohup java -jar spring-petclinic-microservices/spring-petclinic-api-gateway/target/*.jar --server.port=8080 > logs/gateway-service.log 2>&1 &
echo "Waiting for API gateway to start"
wait-for-it --service localhost:8080 --timeout 30

echo "Running Admin server"
nohup java -jar spring-petclinic-microservices/spring-petclinic-admin-server/target/*.jar --server.port=9090 > logs/admin-server.log 2>&1 &
echo "Waiting for Admin server to start"
wait-for-it --service localhost:9090 --timeout 30
