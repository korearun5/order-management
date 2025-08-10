package com.arun.ordermgmt.order.service;

import com.arun.ordermgmt.order.domain.Order;
import com.arun.ordermgmt.order.domain.OrderStatus;
import com.arun.ordermgmt.order.outbox.OutboxEvent;
import com.arun.ordermgmt.order.outbox.OutboxEventRepository;
import com.arun.ordermgmt.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        saveOutboxEvent(savedOrder, "OrderCreated");
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
        saveOutboxEvent(order, "OrderUpdated");
    }

    private void saveOutboxEvent(Order order, String eventType) {
        try {
            OutboxEvent event = new OutboxEvent();
            event.setAggregateId(order.getId().toString());
            event.setEventType(eventType);

            if ("OrderCreated".equals(eventType)) {
                event.setPayload(objectMapper.writeValueAsString(
                        new OrderCreatedEvent(order.getId(), order.getStatus())
                ));
            } else if ("OrderUpdated".equals(eventType)) {
                event.setPayload(objectMapper.writeValueAsString(
                        new OrderUpdatedEvent(order.getId(), order.getStatus())
                ));
            }

            outboxEventRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    // Event DTOs
    private record OrderCreatedEvent(UUID orderId, OrderStatus status) {}
    private record OrderUpdatedEvent(UUID orderId, OrderStatus status) {}

    // Exception
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}