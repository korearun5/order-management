package com.arun.ordermgmt.order.events;

import com.arun.ordermgmt.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class OrderUpdatedEvent {
    @Getter
    private final UUID orderId;
    @Getter
    private final OrderStatus newStatus;

    public OrderUpdatedEvent(UUID orderId, OrderStatus newStatus) {
        this.orderId = orderId;
        this.newStatus = newStatus;
    }
}