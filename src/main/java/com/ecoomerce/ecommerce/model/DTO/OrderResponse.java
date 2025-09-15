package com.ecoomerce.ecommerce.model.DTO;

import java.time.LocalDate;

public record OrderResponse(
        int orderId,
        String customerName,
        String customerEmail,
        String status,
        LocalDate orderDate,
        java.util.List<OrderItemResponse> items
) {
}
