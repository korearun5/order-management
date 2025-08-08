package com.arun.ordermgmt.order.adapter.in.web;


import com.arun.ordermgmt.order.domain.event.OrderCreatedEvent;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final KafkaTemplate<String, OrderCreatedEvent> kafka;

    public OrderController(KafkaTemplate<String, OrderCreatedEvent> kafka) {
        this.kafka = kafka;
    }
    // constructor omitted

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto dto) {
        // Save order entity via JPA, then publish event
        kafka.send("order-created", new OrderCreatedEvent(dto.getId(), dto.getProduct(), dto.getQuantity()));
        return ResponseEntity.accepted().build();
    }
}