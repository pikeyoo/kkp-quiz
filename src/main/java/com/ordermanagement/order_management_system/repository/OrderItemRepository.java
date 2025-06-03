package com.ordermanagement.order_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordermanagement.order_management_system.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
