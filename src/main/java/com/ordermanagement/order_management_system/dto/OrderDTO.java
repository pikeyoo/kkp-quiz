package com.ordermanagement.order_management_system.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO implements Serializable {
  private Long id;
  private String customerName;
  private BigDecimal totalAmount;
  private String status;
  private List<OrderItemDTO> orderItems;
}
