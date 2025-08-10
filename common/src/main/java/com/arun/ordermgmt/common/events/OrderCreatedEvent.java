package com.arun.ordermgmt.common.events;

import com.arun.ordermgmt.common.dtos.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {
    private UUID orderId;
    private String customerId;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalAmount;

    // Constructors
    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, String customerId, List<OrderItemDto> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = items;
    }

    // Getters and setters
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderItemDto> getItems() { return orderItems; }
    public void setItems(List<OrderItemDto> items) { this.orderItems = items; }
}