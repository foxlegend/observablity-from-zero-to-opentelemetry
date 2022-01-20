import py_eureka_client.eureka_client as eureka

eureka_registry = eureka.init(
    eureka_server="http://localhost:8761/eureka/",
    app_name="VETS-SERVICE",
    instance_port=8000
)