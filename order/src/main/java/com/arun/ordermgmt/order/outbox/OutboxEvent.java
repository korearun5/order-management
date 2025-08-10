package com.arun.ordermgmt.order.outbox;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

// OutboxEvent.java
@Entity
@Table(name = "outbox_events")
@Getter
@Setter
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String aggregateType = "Order";

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String eventType;

    @Lob
    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column
    private Instant publishedAt;
}

