package com.ordermanagement.order_management_system.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDTO implements Serializable {
  private Long id;
  private String productName;
  private int quantity;
  private double price;
}
