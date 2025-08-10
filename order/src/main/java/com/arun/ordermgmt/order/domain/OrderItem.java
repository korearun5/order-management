// order/src/main/java/com/arun/ordermgmt/order/domain/OrderItem.java
package com.arun.ordermgmt.order.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class OrderItem {
    @NotNull
    private String productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double price;
}