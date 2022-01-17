package com.github.foxlegend.otel;

import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class BasketApplication {
    
    public static void main(String ... args) {
        AutoConfiguredOpenTelemetrySdk.initialize();
        Quarkus.run(args);
    }
}
