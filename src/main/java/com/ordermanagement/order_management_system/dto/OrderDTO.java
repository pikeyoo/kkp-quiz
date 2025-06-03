package com.ordermanagement.order_management_system.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO implements Serializable {
  private Long id;
  private String customerName;
  private String status;
  private List<OrderItemDTO> orderItems;

  public OrderDTO mapperOrder() {
    OrderDTO order = new OrderDTO();
    
    return order;
  }
}
