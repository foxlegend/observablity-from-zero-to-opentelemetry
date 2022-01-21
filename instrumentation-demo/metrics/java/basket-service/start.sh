#!/usr/bin/env bash

java -Dotel.metrics.exporter=prometheus -Dotel.traces.exporter=none -Dotel.service.name=basket-service -jar target/basket-service-metrics-1.0.0-SNAPSHOT-runner.jar
