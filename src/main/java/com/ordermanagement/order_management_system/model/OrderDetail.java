package com.ordermanagement.order_management_system.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordermanagement.order_management_system.dto.OrderDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail {
  @JsonProperty("order_id")
  private Long orderId;
  @JsonProperty("customer_name")
  private String customerName;
  @JsonProperty("total_amount")
  private BigDecimal totalAmount;
  private List<OrderItem> items;

  public static OrderDetail toResponse(OrderDTO order) {
    List<OrderItem> items = order.getOrderItems().stream()
        .map(OrderItem::toResponse)
        .collect(Collectors.toList());

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrderId(order.getId());
    orderDetail.setCustomerName(order.getCustomerName());
    orderDetail.setTotalAmount(order.getTotalAmount());
    orderDetail.setItems(items);
    return orderDetail;
  }
}
