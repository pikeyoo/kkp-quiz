package com.ordermanagement.order_management_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.order_management_system.entity.OrderEntity;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.model.CreateOrderResponse;
import com.ordermanagement.order_management_system.model.GetOrderResponse;
import com.ordermanagement.order_management_system.model.UpdateStatusOrderRequest;
import com.ordermanagement.order_management_system.model.UpdateStatusOrderResponse;
import com.ordermanagement.order_management_system.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest order) {
    Integer orderId = orderService.createOrder(order);

    CreateOrderResponse createOrderResponse = new CreateOrderResponse();
    createOrderResponse.setOrderId(orderId);
    createOrderResponse.setStatus(OrderStatus.created);
    return ResponseEntity.ok().body(createOrderResponse);
  }

  @GetMapping
  public Page<OrderEntity> getOrders(Pageable pageable) {
    return orderService.getAllOrders(pageable);

    // List<GetOrderResponse> orderResponses = new ArrayList<>();
    // GetOrderResponse orderResponse = new GetOrderResponse();
    // orderResponses.add(orderResponse);
    // return ResponseEntity.ok().body(orderResponses);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long orderId) {
    OrderEntity order = orderService.getOrderById(orderId);
    return ResponseEntity.ok().body(order);
  }

  @PutMapping("/{orderId}/status")
  public ResponseEntity<UpdateStatusOrderResponse> updateOrderStatus(
      @PathVariable Integer orderId,
      @RequestBody UpdateStatusOrderRequest orderStatus) {
    orderService.updateOrderStatus(orderId, orderStatus.getStatus());

    UpdateStatusOrderResponse updateStatusOrderResponse = new UpdateStatusOrderResponse();
    updateStatusOrderResponse.setOrderId(orderId);
    updateStatusOrderResponse.setStatus(orderStatus.getStatus());
    return ResponseEntity.ok().body(updateStatusOrderResponse);
  }
}
