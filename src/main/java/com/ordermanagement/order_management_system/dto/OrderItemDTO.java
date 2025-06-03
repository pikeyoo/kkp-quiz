package com.ordermanagement.order_management_system.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderItemDTO implements Serializable {
  private Long id;
  private String productName;
  private Integer quantity;
  private BigDecimal price;
}
