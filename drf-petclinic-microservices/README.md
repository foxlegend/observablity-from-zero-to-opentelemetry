# AutoInstrumentation

Requis:

* Avoir un collecteur démarré ouvert sur le protocole OTLP GRPC
* Spring Petclinic démarré

## Créer l’environnement

```
poetry install --remove-untracked
poetry shell
```

## Préparer l’environnement Python

```
cd drf-petclinic-microservices
python manage.py migrate
python manage.py loaddata vets/fixtures/*
```

## Préparer l’autoinstrumentation :)

```
opentelemetry-bootstrap --action=install
```

```
# Définition du nom du service
export OTEL_RESOURCE_ATTRIBUTES=service.name=vets-service-python
# Définition de l’exporter
export OTEL_TRACES_EXPORTER=otlp
# Éventuellement un niveau de logs
export OTEL_LOG_LEVEL=debug

# Nécessaire avec Django
export DJANGO_SETTINGS_MODULE=drf_petclinic_microservices.settings
```

## Lancer le serveur avec l’autinstrumentation

```
opentelemetry-instrument python manage.py runserver --noreload
```