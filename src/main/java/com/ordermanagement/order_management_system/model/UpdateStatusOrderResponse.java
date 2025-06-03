package com.ordermanagement.order_management_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordermanagement.order_management_system.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusOrderResponse {
    @JsonProperty("order_id")
    private Integer orderId;
    private OrderStatus status;
}
