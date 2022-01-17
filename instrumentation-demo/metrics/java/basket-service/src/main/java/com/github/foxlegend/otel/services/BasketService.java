package com.github.foxlegend.otel.services;

import java.util.Collections;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.github.foxlegend.otel.models.Basket;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

import static io.opentelemetry.api.common.AttributeKey.stringKey;

@ApplicationScoped
public class BasketService {
    private static final Meter meter = GlobalOpenTelemetry
            .getMeter("basket.metrics");
    private static LongCounter basketCounter = 
                                meter.counterBuilder("basket_creation")
                                    .setDescription("Compte les paniers créés")
                                    .setUnit("basket")
                                    .build();

    @PostConstruct
    void businessMetrics() {
        meter.gaugeBuilder("checkout_amount")
        .setDescription("Compte le montant des paniers")
        .setUnit("$")
        .buildWithCallback(result -> {
            result.record(getOpenBasketAmount(), Attributes.of(stringKey("type"), "OPEN"));
            result.record(getClosedBasketAmount(), Attributes.of(stringKey("type"), "CLOSED"));
        });
    }

    public Basket createBasket(Basket basket) {
        basket.id = genId();
        basket.state = "OPEN";
        basketRepository.put(basket.id, basket);

        basketCounter.add(1, Attributes.of(stringKey("type"), "OPEN"));

        return basket;
    }

    public Basket checkoutBasket(Long id) {
        var basket = basketRepository.get(id);
        if (basket == null) {
            return null;
        }

        basket.state = "CLOSED";

        basketCounter.add(1, Attributes.of(stringKey("type"), "CLOSED"));

        return basket;
    }

    public Basket getBasketById(Long id) {
        return basketRepository.get(id);
    }

    private static HashMap<Long, Basket> basketRepository = new HashMap<>();

    private static double getOpenBasketAmount() {
        return basketRepository.entrySet().stream().filter(e -> e.getValue().state.equalsIgnoreCase("OPEN")).mapToDouble(e -> e.getValue().amount()).sum();
    }

    private static double getClosedBasketAmount() {
        return basketRepository.entrySet().stream().filter(e -> e.getValue().state.equalsIgnoreCase("CLOSED")).mapToDouble(e -> e.getValue().amount()).sum();
    }

    private Long genId() {
        if (basketRepository.isEmpty()) {
            return 1l;
        }
        return Collections.max(basketRepository.keySet()) + 1;
    }
}
