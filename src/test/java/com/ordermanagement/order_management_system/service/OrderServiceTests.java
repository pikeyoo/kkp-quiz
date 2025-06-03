package com.ordermanagement.order_management_system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ordermanagement.order_management_system.dto.OrderDTO;
import com.ordermanagement.order_management_system.entity.OrderEntity;
import com.ordermanagement.order_management_system.entity.OrderItemEntity;
import com.ordermanagement.order_management_system.enums.OrderStatus;
import com.ordermanagement.order_management_system.model.CreateOrderRequest;
import com.ordermanagement.order_management_system.model.OrderItem;
import com.ordermanagement.order_management_system.repository.OrderItemRepository;
import com.ordermanagement.order_management_system.repository.OrderRepository;
import com.ordermanagement.order_management_system.utils.CommonUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CommonUtils commonUtils;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder_Success() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("John Doe");
        List<OrderItem> items = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setProductName("Item 1");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal(10.99));
        OrderItem item2 = new OrderItem();
        item2.setProductName("Item 2");
        item2.setQuantity(3);
        item2.setPrice(new BigDecimal(5.99));
        items.add(item1);
        items.add(item2);
        request.setItems(items);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        Long orderId = orderService.createOrder(request);

        assertEquals(1L, orderId);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderItemRepository, times(2)).save(any(OrderItemEntity.class));
    }

    @Test
    public void testCreateOrder_NullItemsList() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("John Doe");
        request.setItems(null);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(request));
    }

    @Test
    public void testCreateOrder_InvalidTotalAmountCalculation() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("John Doe");
        List<OrderItem> items = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setProductName("Item 1");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal(10.99));
        OrderItem item2 = new OrderItem();
        item2.setProductName("Item 2");
        item2.setQuantity(3);
        item2.setPrice(new BigDecimal(5.99));
        items.add(item1);
        items.add(item2);
        request.setItems(items);

        when(commonUtils.getCurrentDateTime()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(request));
    }


    @Test
    public void testGetOrderById_ExistingOrder() {
        Long orderId = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setCustomerName("John Doe");
        orderEntity.setTotalAmount(new BigDecimal("10.99"));
        orderEntity.setStatus("created");
        orderEntity.setOrderItems(new ArrayList<>());
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        OrderDTO orderDTO = orderService.getOrderById(orderId);

        assertEquals(orderId, orderDTO.getId());
        assertEquals("John Doe", orderDTO.getCustomerName());
        assertEquals(new BigDecimal("10.99"), orderDTO.getTotalAmount());
        assertEquals("created", orderDTO.getStatus());
    }

    @Test
    public void testGetOrderById_NonExistingOrder() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    public void testGetOrderById_NullOrderRepositoryResponse() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderService.getOrderById(orderId));
    }


    @Test
    public void testGetAllOrders_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(new OrderEntity());
        orderEntities.add(new OrderEntity());
        Page<OrderEntity> page = new PageImpl<>(orderEntities, pageable, 2);
        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<OrderDTO> result = orderService.getAllOrders(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllOrders_EmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> page = new PageImpl<>(new ArrayList<>(), pageable, 0);
        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<OrderDTO> result = orderService.getAllOrders(pageable);

        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());
    }


    @Test
    public void testGetAllOrders_RepositoryThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(orderRepository.findAll(pageable)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> orderService.getAllOrders(pageable));
    }


    @Test
    public void testUpdateOrderStatus_ValidOrderIdAndStatus() {
        Long orderId = 1L;
        OrderStatus status = OrderStatus.completed;
        LocalDateTime now = LocalDateTime.now();

        when(commonUtils.getCurrentDateTime()).thenReturn(now);

        orderService.updateOrderStatus(orderId, status);

        verify(orderRepository, times(1)).updateStatusById(orderId, status.toString(), now);
    }

    @Test
    public void testUpdateOrderStatus_InvalidStatus_Null() {
        Long orderId = 1L;
        OrderStatus status = null;

        assertThrows(NullPointerException.class, () -> orderService.updateOrderStatus(orderId, status));
    }
}
