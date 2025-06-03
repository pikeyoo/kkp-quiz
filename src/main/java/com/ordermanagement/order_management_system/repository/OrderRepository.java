package com.ordermanagement.order_management_system.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ordermanagement.order_management_system.entity.OrderEntity;

import jakarta.transaction.Transactional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  @Modifying
  @Transactional
  @Query("UPDATE OrderEntity SET status = :status, updatedAt = :dateTime WHERE id = :id")
  void updateStatusById(Integer id, String status, LocalDateTime dateTime);
}
