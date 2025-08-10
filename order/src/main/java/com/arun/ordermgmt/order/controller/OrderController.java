    package com.arun.ordermgmt.order.controller;

    import com.arun.ordermgmt.order.domain.Order;
    import com.arun.ordermgmt.order.domain.OrderStatus; // Add this import
    import com.arun.ordermgmt.order.service.OrderService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.net.URI;
    import java.util.UUID;

    @RestController
    @RequestMapping("/orders")
    public class OrderController {
        private final OrderService orderService;

        public OrderController(OrderService orderService) {
            this.orderService = orderService;
        }

        @PostMapping
        public ResponseEntity<Order> createOrder(@RequestBody Order order) {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.created(URI.create("/orders/" + createdOrder.getId()))
                    .body(createdOrder);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Order> getOrder(@PathVariable("id") UUID id) {
            return ResponseEntity.ok(orderService.getOrderById(id));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<Void> updateOrderStatus(
                @PathVariable UUID id,
                @RequestParam OrderStatus status) {
            orderService.updateOrderStatus(id, status);
            return ResponseEntity.noContent().build();
        }
    }