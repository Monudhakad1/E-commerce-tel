package com.ecoomerce.ecommerce.model.DTO;

public record OrderRequest(
        String customerName,
        String customerEmail,
        java.util.List<OrderItemRequest> items
) {
}
