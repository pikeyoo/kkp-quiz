package com.ordermanagement.order_management_system.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.dto.OrderItemDTO;
import com.ordermanagement.order_management_system.entity.OrderEntity;
import com.ordermanagement.order_management_system.entity.OrderItemEntity;

public class OrderMapper {

  public static OrderDTO toDTO(OrderEntity order) {
    List<OrderItemDTO> orderItems = order.getOrderItems() == null ? null
        : order.getOrderItems().stream()
            .map(OrderMapper::toDTO)
            .collect(Collectors.toList());

    return new OrderDTO(
        order.getId(),
        order.getCustomerName(),
        order.getTotalAmount(),
        order.getStatus(),
        orderItems);
  }

  public static OrderItemDTO toDTO(OrderItemEntity item) {
    return new OrderItemDTO(
        item.getId(),
        item.getProductName(),
        item.getQuantity(),
        item.getPrice());
  }
}
