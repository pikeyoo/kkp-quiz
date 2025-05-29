package com.ordermanagement.order_management_system.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class CreateOrderRequest {
    @JsonProperty("customer_name")
    private String customerName;
    private List<OrderItem> items;
}
