package com.arun.ordermgmt.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private String productId;
    private Integer quantity;
    private Double price;
}