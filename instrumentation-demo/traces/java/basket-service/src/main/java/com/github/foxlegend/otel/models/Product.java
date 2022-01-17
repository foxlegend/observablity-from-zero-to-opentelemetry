package com.github.foxlegend.otel.models;

import java.util.Locale;

import com.github.javafaker.Faker;

public class Product {
    public Long id;
    public String name;
    public Double price;

    private static Faker faker = new Faker(Locale.FRANCE);

    public static Product randomProduct() {
        var product = new Product();
        product.id = (long) ((Math.random() * 100) + 1);
        product.name = faker.commerce().productName();
        product.price = Double.parseDouble(faker.commerce().price(0.99, 999.0));
        return product;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name=\'" + name + '\'' +
            ", price=\'" + price + '\'' +
            '}';
    }
}
