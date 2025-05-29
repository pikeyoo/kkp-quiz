package com.ordermanagement.order_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import jakarta.annotation.PostConstruct;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "com.ordermanagement.order_management_system")
public class OrderManagementSystemApplication {

	@PostConstruct
    public void init() {
        System.out.println("Server started at path: " + System.getProperty("user.dir"));
    }

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementSystemApplication.class, args);
	}

}
