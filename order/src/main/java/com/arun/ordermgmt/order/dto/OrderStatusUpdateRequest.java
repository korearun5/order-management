package com.arun.ordermgmt.order.dto;

import com.arun.ordermgmt.common.model.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private OrderStatus status;
}