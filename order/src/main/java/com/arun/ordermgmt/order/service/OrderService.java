package com.arun.ordermgmt.order.service;

import com.arun.ordermgmt.common.events.OrderCreatedEvent;
import com.arun.ordermgmt.common.dtos.OrderItemDto;
import com.arun.ordermgmt.common.model.OrderStatus;
import com.arun.ordermgmt.order.domain.Order;
import com.arun.ordermgmt.order.domain.OrderItem;
import com.arun.ordermgmt.order.dto.OrderRequest;
import com.arun.ordermgmt.order.exception.OrderNotFoundException;
import com.arun.ordermgmt.order.outbox.OutboxEvent;
import com.arun.ordermgmt.order.outbox.OutboxEventRepository;
import com.arun.ordermgmt.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Order createOrder(OrderRequest request) {
        // Convert DTO to Entity
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());

        // Convert OrderItem DTOs to Entities
        List<OrderItem> orderItems = request.getItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        saveOutboxEvent(savedOrder, "OrderCreated");
        return savedOrder;
    }
    private OrderItem convertToOrderItemEntity(com.arun.ordermgmt.common.model.OrderItem dto) {
        OrderItem entity = new OrderItem();
        entity.setProductId(dto.getProductId().toString());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice().doubleValue());
        return entity;
    }

    // ... other methods ...
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
    public Order updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    private void saveOutboxEvent(Order order, String eventType) {
        try {
            OutboxEvent event = new OutboxEvent();
            event.setAggregateId(order.getId().toString());
            event.setEventType(eventType);

            if ("OrderCreated".equals(eventType)) {
                // Convert to shared DTO
                List<OrderItemDto> items = order.getItems().stream()
                        .map(item -> new OrderItemDto(
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice()))
                        .collect(Collectors.toList());

                OrderCreatedEvent eventData = new OrderCreatedEvent(
                        order.getId(),
                        order.getCustomerId(),
                        items
                );

                event.setPayload(objectMapper.writeValueAsString(eventData));
            }
            // Handle other event types...

            outboxEventRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}