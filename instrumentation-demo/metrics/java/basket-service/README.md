# basket-service Project

Start the project :

```
java -Dotel.metrics.exporter=prometheus -Dotel.traces.exporter=none -Dotel.service.name=basket-service -jar target/basket-service-metrics-1.0.0-SNAPSHOT-runner.jar
```

Curl :

```
http POST :18080/api/basket
http POST :18080/api/basket/checkout/1
```

Read the metrics :)

http://localhost:9464/metrics