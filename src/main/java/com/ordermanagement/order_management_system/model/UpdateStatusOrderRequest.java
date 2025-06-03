package com.ordermanagement.order_management_system.model;

import com.ordermanagement.order_management_system.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class UpdateStatusOrderRequest {
  private OrderStatus status;
}
