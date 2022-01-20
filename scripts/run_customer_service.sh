#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

OTEL_OPTIONS="-Dotel.logs.exporter=otlp -Dotel.instrumentation.jdbc-datasource.enabled=true"

echo "Running Customer microservice"
nohup java -javaagent:agent/opentelemetry-javaagent-1.10.0.jar -Dotel.service.name=petclinic-customers-service ${OTEL_OPTIONS} -jar spring-petclinic-microservices/spring-petclinic-customers-service/target/*.jar --server.port=8081 > logs/customers-service.log 2>&1 &