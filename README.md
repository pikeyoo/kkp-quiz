# Order Management System

## Overview

This is a Java-based Order Management System that provides a RESTful API for managing orders. The system allows users to create, update, and retrieve orders.

## Features

- Create orders
- Update order status
- Retrieve orders by ID
- Retrieve all orders

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Spring Boot 2.5 or higher
- PostgreSQL 12 or higher

## Setup

- Build the project and Install the artifact locally
``` 
mvn clean install
```

- Setup config for connect Database and flyway create table

```properties
# database
spring.datasource.url=jdbc:postgresql://<db_host>:<db_port>/<db_name>
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=none

# flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migrations
```

- Start the application

- After start application flyway auto run can check database table

- Run api with swagger
```
http://localhost:5000/swagger-ui/index.html
```

## API Documentation

### Create Order

- **Request** POST /orders

**Request Body:**

```json
{
  "customer_name": "John Doe",
  "items": [
    {
      "product_name": "Product A",
      "quantity": 5,
      "price": 50.0
    },
    {
      "product_name": "Product B",
      "quantity": 1,
      "price": 100.0
    }
  ]
}
```

**Response:**

```json
{
  "status": "created",
  "order_id": 28
}
```

### Update Order Status

- **Request** PUT /orders/{orderId}/status

**Request Body:**

```Json
{ 
  "status": "completed"
}
```

**Response**

```json
{
  "status": "completed",
  "order_id": 27
}
```

### Retrieve Order by ID

- **Request** GET /orders/{orderId}

**Response**

```Json
{
  "items": [
    {
      "quantity": 5,
      "price": 50.00,
      "product_name": "Product A"
    },
    {
      "quantity": 1,
      "price": 100.00,
      "product_name": "Product B"
    }
  ],
  "order_id": 28,
  "customer_name": "John Doe",
  "total_amount": 350.00
}
```

### Retrieve All Orders

- **Request** GET /orders

**Response**

```Json
{
  "orders": [
    {
      "items": [
        {
          "quantity": 2,
          "price": 50.00,
          "product_name": "Product A"
        },
        {
          "quantity": 1,
          "price": 100.00,
          "product_name": "Product B"
        }
      ],
      "order_id": 3,
      "customer_name": "John Doe",
      "total_amount": 150.00
    },
    {
      "items": [
        {
          "quantity": 2,
          "price": 50.00,
          "product_name": "Product A"
        },
        {
          "quantity": 1,
          "price": 100.00,
          "product_name": "Product B"
        }
      ],
      "order_id": 2,
      "customer_name": "John Doe",
      "total_amount": 150.00
    }
  ],
  "total": 27
}
```

## Testing

The system includes unit tests and integration tests using JUnit and Mockito.

## Acknowledgments

Spring Boot
PostgreSQL
Mockito
JUnit
