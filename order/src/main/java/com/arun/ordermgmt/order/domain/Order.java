package com.arun.ordermgmt.order.domain;

import com.arun.ordermgmt.common.model.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    private Instant orderDate = Instant.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items;

    public Order() {}
}