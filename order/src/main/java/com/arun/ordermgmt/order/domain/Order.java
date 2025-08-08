package com.arun.ordermgmt.order.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private CustomerId customerId;

    @ElementCollection
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Domain method
    public void placeOrder() {
        validate();
        this.status = OrderStatus.PENDING;
        DomainEvents.publish(new OrderPlacedEvent(this));
    }
}