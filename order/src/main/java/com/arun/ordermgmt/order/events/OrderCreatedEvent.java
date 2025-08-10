package com.arun.ordermgmt.order.events;

import com.arun.ordermgmt.order.domain.Order;

// OrderCreatedEvent.java
public class OrderCreatedEvent {
    private Order order;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    // Getters and setters
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}