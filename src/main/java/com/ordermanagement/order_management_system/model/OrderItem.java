package com.ordermanagement.order_management_system.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordermanagement.order_management_system.dto.OrderItemDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    @JsonProperty("product_name")
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    public static OrderItem toResponse(OrderItemDTO itemDTO) {
      OrderItem item = new OrderItem();
      item.setProductName(itemDTO.getProductName());
      item.setQuantity(itemDTO.getQuantity());
      item.setPrice(itemDTO.getPrice());
      return item;
    }
}
