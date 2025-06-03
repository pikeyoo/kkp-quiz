package com.ordermanagement.order_management_system.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderAllResponse {
  private List<OrderDetail> orders;
  private Long total;
}
