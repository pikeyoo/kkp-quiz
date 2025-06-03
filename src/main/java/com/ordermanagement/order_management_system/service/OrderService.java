package com.ordermanagement.order_management_system.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.entity.OrderEntity;
import com.ordermanagement.order_management_system.entity.OrderItemEntity;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.mapper.OrderMapper;
import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.repository.OrderItemRepository;
import com.ordermanagement.order_management_system.repository.OrderRepository;
import com.ordermanagement.order_management_system.utils.CommonUtils;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderItemRepository orderItemRepository;

  public Long createOrder(CreateOrderRequest order) {
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

  public OrderDTO getOrderById(Long orderId) {
    OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());

    return new OrderDTO(orderEntity.getId(),
        orderEntity.getCustomerName(),
        orderEntity.getTotalAmount(),
        orderEntity.getStatus(),
        orderEntity.getOrderItems().stream().map(OrderMapper::toDTO).collect(Collectors.toList()));
  }

  public Page<OrderDTO> getAllOrders(Pageable pageable) {
    return orderRepository.findAll(pageable).map(OrderMapper::toDTO);
  }

  public void updateOrderStatus(Integer orderId, OrderStatus status) {
    orderRepository.updateStatusById(orderId, status.toString(), CommonUtils.getCurrentDateTime());
  }
}
