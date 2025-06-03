package com.ordermanagement.order_management_system.mapper;


public class OrderMapper {

    // public static OrderDTO toDTO(OrderEntity order) {
    //     List<OrderItemDTO> itemDTOs = order.getOrderItems() == null ? null :
    //             order.getOrderItems().stream()
    //             .map(OrderMapper::toDTO)
    //             .collect(Collectors.toList());

    //     return new OrderDTO(
    //             order.getId(),
    //             order.getCustomerName(),
    //             order.getStatus(),
    //             itemDTOs
    //     );
    // }

    // public static OrderItemDTO toDTO(OrderItem item) {
    //     return new OrderItemDTO(
    //             item.getId(),
    //             item.getProductName(),
    //             item.getQuantity(),
    //             item.getPrice()
    //     );
    // }
}
