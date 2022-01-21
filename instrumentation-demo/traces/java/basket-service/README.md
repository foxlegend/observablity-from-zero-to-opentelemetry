# basket-service Project

Start the project :

```
java -Dotel.traces.exporter=logging -Dotel.service.name=basket-service -jar target/basket-service-traces-1.0.0-SNAPSHOT-runner.jar
```

Curl :

```
http POST :18080/api/basket
http POST :18080/api/basket/checkout/1
```

Read the metrics :)

http://localhost:9464/metrics