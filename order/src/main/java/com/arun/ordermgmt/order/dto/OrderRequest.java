package com.arun.ordermgmt.order.dto;

import com.arun.ordermgmt.common.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String customerId;
    private List<OrderItem> items;
}