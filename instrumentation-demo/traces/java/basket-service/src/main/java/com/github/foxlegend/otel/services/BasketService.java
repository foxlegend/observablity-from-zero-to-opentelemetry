package com.github.foxlegend.otel.services;

import java.util.Collections;
import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;

import com.github.foxlegend.otel.models.Basket;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;


@ApplicationScoped
public class BasketService {
    private static final Tracer tracer = GlobalOpenTelemetry.getTracer("basket.traces");

    public Basket createBasket(Basket basket) {
        var span = tracer.spanBuilder("BasketService::createBasket").startSpan();
        try (var scope = span.makeCurrent()) {
            basket.id = genId();
            basket.state = "OPEN";
            
            span.addEvent("Start PUT Basket to repository");
            basketRepository.put(basket.id, basket);
            span.addEvent("End PUT Basket to repository");

            return basket;
        } finally {
            span.end();
        }
    }

    public Basket checkoutBasket(Long id) { 
        var basket = basketRepository.get(id);
        if (basket == null) {
            return null;
        }

        basket.state = "CLOSED";

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
