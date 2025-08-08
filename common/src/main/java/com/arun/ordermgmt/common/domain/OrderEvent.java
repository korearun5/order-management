package com.arun.ordermgmt.common.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        OrderStatus status,
        List<OrderItem> items,
        Instant timestamp
) implements Serializable {}
