// common/src/main/java/com/arun/ordermgmt/common/domain/OrderEvent.java
package com.arun.ordermgmt.common.domain;

import com.arun.ordermgmt.common.model.OrderItem;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class OrderEvent {
    private UUID orderId;
    private String customerId;
    private List<OrderItem> items; // Use common model, not JPA entity
}