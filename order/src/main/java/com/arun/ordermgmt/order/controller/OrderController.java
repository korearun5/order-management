package com.arun.ordermgmt.order.controller;

import com.arun.ordermgmt.common.model.OrderStatus;
import com.arun.ordermgmt.order.domain.Order;
import com.arun.ordermgmt.order.dto.OrderRequest;
import com.arun.ordermgmt.order.dto.OrderStatusUpdateRequest;
import com.arun.ordermgmt.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(
                orderId,
                request.getStatus()
        ));
    }
}