package com.ordermanagement.order_management_system.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.model.CreateOrderResponse;
import com.ordermanagement.order_management_system.model.GetOrderAllResponse;
import com.ordermanagement.order_management_system.model.GetOrderResponse;
import com.ordermanagement.order_management_system.model.OrderDetail;
import com.ordermanagement.order_management_system.model.OrderItem;
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
    Long orderId = orderService.createOrder(order);

    CreateOrderResponse createOrderResponse = new CreateOrderResponse();
    createOrderResponse.setOrderId(orderId);
    createOrderResponse.setStatus(OrderStatus.created);
    return ResponseEntity.ok().body(createOrderResponse);
  }

  @GetMapping
  public ResponseEntity<GetOrderAllResponse> getOrders(Pageable pageable) {
    Pageable page = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
    Page<OrderDTO> orderPage = orderService.getAllOrders(page);

    GetOrderAllResponse orderAllResponses = new GetOrderAllResponse();
    orderAllResponses.setOrders(
      orderPage.getContent().stream().map(OrderDetail::toResponse).collect(Collectors.toList()));
    orderAllResponses.setTotal(orderPage.getTotalElements());
    return ResponseEntity.ok().body(orderAllResponses);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<GetOrderResponse> getOrderById(@PathVariable Long orderId) {
    OrderDTO order = orderService.getOrderById(orderId);

    GetOrderResponse getOrderResponse = new GetOrderResponse();
    getOrderResponse.setOrderId(order.getId());
    getOrderResponse.setCustomerName(order.getCustomerName());
    getOrderResponse.setTotalAmount(order.getTotalAmount());
    getOrderResponse.setItems(
        order.getOrderItems().stream().map(OrderItem::toResponse).collect(Collectors.toList()));
    return ResponseEntity.ok().body(getOrderResponse);
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
