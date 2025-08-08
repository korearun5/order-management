package com.arun.ordermgmt.inventory;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// inventory-service/src/main/java/com/example/inventory/OrderEventsConsumer.java
@Bean
public InventoryConsumer<OrderEvent> orderEvents() {
    return event -> {
        if (event instanceof OrderPlacedEvent) {
            inventoryService.reserveItems(event);
        }
    };
}

