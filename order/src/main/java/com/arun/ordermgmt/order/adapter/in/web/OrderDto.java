package com.arun.ordermgmt.order.adapter.in.web;

public class OrderDto {
    private Long id;
    private String product;
    private int quantity;

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // constructor, getters, setters
}