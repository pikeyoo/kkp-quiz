package com.ordermanagement.order_management_system.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class CommonUtils {
  public LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now();
  }
}
