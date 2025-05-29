package com.ordermanagement.order_management_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderResponse {
    @JsonProperty("order_id")
    private Integer orderId;
    private String status;
}
