package com.arun.ordermgmt.order.infra;


@Entity
public class OutboxEvent {
    @Id
    @GeneratedValue
    private Long id;

    private String aggregateType;
    private UUID aggregateId;
    private String type;
    private byte[] payload;
    private boolean published;
}