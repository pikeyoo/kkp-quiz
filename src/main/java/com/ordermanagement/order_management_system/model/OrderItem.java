package com.ordermanagement.order_management_system.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    @JsonProperty("product_name")
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
