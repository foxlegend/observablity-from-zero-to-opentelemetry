#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

OTEL_OPTIONS="-Dotel.logs.exporter=otlp -Dotel.instrumentation.jdbc-datasource.enabled=true"

echo "Running API gateway"
nohup java -javaagent:agent/opentelemetry-javaagent-1.10.0.jar -Dotel.service.name=petclinic-api-gateway ${OTEL_OPTIONS} -jar spring-petclinic-microservices/spring-petclinic-api-gateway/target/*.jar --server.port=8080 > logs/gateway-service.log 2>&1 &
