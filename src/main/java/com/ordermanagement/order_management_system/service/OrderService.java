package com.ordermanagement.order_management_system.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.entity.OrderEntity;
import com.ordermanagement.order_management_system.entity.OrderItemEntity;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.model.OrderItem;
import com.ordermanagement.order_management_system.repository.OrderItemRepository;
import com.ordermanagement.order_management_system.repository.OrderRepository;
import com.ordermanagement.order_management_system.utils.CommonUtils;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderItemRepository orderItemRepository;

  public Integer createOrder(CreateOrderRequest order) {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setCustomerName(order.getCustomerName());
    BigDecimal totalAmount = order.getItems().stream()
        .map(item -> new BigDecimal(item.getQuantity()).multiply(item.getPrice()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    orderEntity.setTotalAmount(totalAmount);
    orderEntity.setStatus(OrderStatus.created.toString());
    orderEntity.setCreatedAt(CommonUtils.getCurrentDateTime());
    OrderEntity iOrderEntity = orderRepository.save(orderEntity);

    order.getItems().stream().forEach(item -> {
      OrderItemEntity orderItemEntity = new OrderItemEntity();
      orderItemEntity.setOrder(iOrderEntity);
      orderItemEntity.setProductName(item.getProductName());
      orderItemEntity.setQuantity(item.getQuantity());
      orderItemEntity.setPrice(item.getPrice());
      orderItemRepository.save(orderItemEntity);
    });

    return iOrderEntity.getId();
  }

  public OrderEntity getOrderById(Long orderId) {
    return orderRepository.findById(orderId).orElse(null);
  }

  public Page<OrderEntity> getAllOrders(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }

  public void updateOrderStatus(Integer orderId, OrderStatus status) {
    orderRepository.updateStatusById(orderId, status.toString(), CommonUtils.getCurrentDateTime());
  }
}
