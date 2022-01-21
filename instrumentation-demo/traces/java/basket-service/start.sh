#!/usr/bin/env bash

java -Dotel.traces.exporter=logging -Dotel.service.name=basket-service -jar target/basket-service-traces-1.0.0-SNAPSHOT-runner.jar
