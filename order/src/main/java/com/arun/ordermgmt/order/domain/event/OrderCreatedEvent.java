package com.arun.ordermgmt.order.domain.event;

public class OrderCreatedEvent {
    private Long orderId;
    private String product;
    private int quantity;

    public OrderCreatedEvent(Long id, String product, int quantity) {
        orderId = id;
        this.product = product;
        this.quantity = quantity;
    }

    // constructor, getters, setters
}
