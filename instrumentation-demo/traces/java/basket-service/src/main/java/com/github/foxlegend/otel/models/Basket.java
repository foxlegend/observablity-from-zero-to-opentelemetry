package com.github.foxlegend.otel.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {
    public Long id;
    public String state;
    public List<Product> products = new ArrayList<>();

    public double amount() {
        if (products.isEmpty()) {
            return 0;
        }
        return products.stream().mapToDouble(p -> p.price).sum();
    }

    public static Basket randomBasket() {
        Basket basket = new Basket();

        var randomProductsCount = (int) ((Math.random() * 9) + 1);
        for (int i = 0; i < randomProductsCount; i++) {
            basket.products.add(Product.randomProduct());
        }
        return basket;
    }

    @Override
    public String toString() {
        return "Basket{" +
            "id=" + id +
            ", state='" + state + '}' +
            "[" + products.stream().map(p -> p.toString()).collect(Collectors.joining(", ")) + "]" +
            '}';
    }
}
