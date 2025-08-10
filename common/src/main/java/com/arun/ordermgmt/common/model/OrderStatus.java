package com.arun.ordermgmt.common.model;

public enum OrderStatus {
    CREATED,         // Initial state
    PROCESSING,      // Order being processed
    PAYMENT_PENDING, // Waiting for payment
    INVENTORY_HOLD,  // Inventory reserved
    SHIPPED,         // Sent to customer
    DELIVERED,       // Received by customer
    CANCELLED,       // Order cancelled
    FAILED           // Processing failed
}