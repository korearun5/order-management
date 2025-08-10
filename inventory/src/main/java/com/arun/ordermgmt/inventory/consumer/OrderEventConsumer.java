package com.arun.ordermgmt.inventory.consumer;

import com.arun.ordermgmt.inventory.repository.InventoryRepository;
import com.arun.ordermgmt.order.domain.OrderCreatedEvent;
import com.arun.ordermgmt.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {
    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received Order Created Event: {}", event.getOrderId());

        for (OrderItem item : event.getItems()) {
            inventoryRepository.findByProductId(item.getProductId())
                    .ifPresentOrElse(
                            inventory -> {
                                if (inventory.getQuantity() >= item.getQuantity()) {
                                    inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                                    inventoryRepository.save(inventory);
                                    log.info("Updated inventory for product {}: New quantity = {}",
                                            item.getProductId(), inventory.getQuantity());
                                } else {
                                    log.error("Insufficient inventory for product {}: Requested {}, Available {}",
                                            item.getProductId(), item.getQuantity(), inventory.getQuantity());
                                    // Handle insufficient inventory (e.g., send compensation event)
                                }
                            },
                            () -> log.error("Product not found in inventory: {}", item.getProductId())
                    );
        }
    }
}