package com.ordermanagement.order_management_system.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.model.CreateOrderResponse;

@RestController
public class OrderController {
    @GetMapping("/orders")
    public ResponseEntity<String> getOrders() {
        return ResponseEntity.ok().body("Orders found!");
    }

    @PostMapping("/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        CreateOrderResponse createOrderResponse = new CreateOrderResponse();
        return ResponseEntity.ok().body(createOrderResponse);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<String> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(String.format("Order ID: %d", orderId));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(String.format("Order ID: %d status updated!", orderId));
    }
}
