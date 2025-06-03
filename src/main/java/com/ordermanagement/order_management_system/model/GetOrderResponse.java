package com.ordermanagement.order_management_system.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderResponse {
  @JsonProperty("order_id")
  private String orderId;
  @JsonProperty("customer_name")
  private String customerName;
  @JsonProperty("total_amount")
  private String totalAmount;
  private List<OrderItem> items;
}
