package com.azaria.somemarket.model;

import com.azaria.somemarket.dto.ProductResponse;

import java.math.BigDecimal;

public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private boolean availability;

    public Product (long id, String name, BigDecimal price, boolean availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public Product (ProductResponse dbProduct) {
        this.id = Integer.parseInt(dbProduct.id());
        this.name = dbProduct.name();
        this.price = dbProduct.price();
        this.availability = dbProduct.availability();
    }

    public long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public boolean getAvailability () {
        return availability;
    }
}
