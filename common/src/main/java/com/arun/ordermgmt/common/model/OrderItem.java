package com.arun.ordermgmt.common.model;


import lombok.Data;
import java.math.BigDecimal;
@Data
public class OrderItem {
    private Long productId;
    private int quantity;
    private BigDecimal price;
}