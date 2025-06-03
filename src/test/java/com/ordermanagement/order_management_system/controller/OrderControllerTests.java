package com.ordermanagement.order_management_system.controller;

import com.ordermanagement.order_management_system.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTests {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testCreateOrder_Success() {
        CreateOrderRequest request = new CreateOrderRequest();
        Long orderId = 1L;
        when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(orderId);

        ResponseEntity<CreateOrderResponse> response = orderController.createOrder(request);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateOrder_ServiceThrowsException() {
        CreateOrderRequest request = new CreateOrderRequest();
        when(orderService.createOrder(any(CreateOrderRequest.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> orderController.createOrder(request));
    }


    @Test
    public void testGetOrders_Success() {
        PageRequest pageable = PageRequest.of(1, 5);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orderDTOs.add(new OrderDTO(1L, "name 1", new BigDecimal(100), OrderStatus.completed.toString(), new ArrayList<>()));
        Page<OrderDTO> orderPage = new PageImpl<>(orderDTOs, pageable, 1);
        when(orderService.getAllOrders(any(Pageable.class))).thenReturn(orderPage);

        ResponseEntity<GetOrderAllResponse> response = orderController.getOrders(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(6, response.getBody().getTotal());
        assertEquals(1, response.getBody().getOrders().size());
    }

    @Test
    public void testGetOrders_EmptyPageContent() {
      PageRequest pageable = PageRequest.of(1, 5);
        when(orderService.getAllOrders(any(Pageable.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> orderController.getOrders(pageable));
    }


    @Test
    public void testGetOrderById_Success() {
        Long orderId = 1L;
        String name = "John Doe";
        BigDecimal amount = new BigDecimal("10.99");
        OrderDTO orderDTO = new OrderDTO(orderId, name, amount, OrderStatus.created.toString(), new ArrayList<>());
        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);

        ResponseEntity<GetOrderResponse> response = orderController.getOrderById(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderId, response.getBody().getOrderId());
    }

    @Test
    public void testGetOrderById_NonExistentOrder() {
        Long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderController.getOrderById(orderId));
    }


    @Test
    public void testUpdateOrderStatus_Success() {
        Long orderId = 1L;
        UpdateStatusOrderRequest orderStatus = new UpdateStatusOrderRequest();
        orderStatus.setStatus(OrderStatus.completed);

        ResponseEntity<UpdateStatusOrderResponse> response = orderController.updateOrderStatus(orderId, orderStatus);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderId, response.getBody().getOrderId());
        assertEquals(orderStatus.getStatus(), response.getBody().getStatus());
    }

    @Test
    public void testUpdateOrderStatus_RuntimeException() {
        Long orderId = 1L;
        UpdateStatusOrderRequest orderStatus = new UpdateStatusOrderRequest();
        orderStatus.setStatus(OrderStatus.completed);
        doThrow(new RuntimeException()).when(orderService).updateOrderStatus(orderId, OrderStatus.completed);

        assertThrows(RuntimeException.class, () -> orderController.updateOrderStatus(orderId, orderStatus));
    }
}
