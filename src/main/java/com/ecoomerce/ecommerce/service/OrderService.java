package com.ecoomerce.ecommerce.service;

import com.ecoomerce.ecommerce.model.DTO.OrderItemRequest;
import com.ecoomerce.ecommerce.model.DTO.OrderItemResponse;
import com.ecoomerce.ecommerce.model.DTO.OrderRequest;
import com.ecoomerce.ecommerce.model.DTO.OrderResponse;
import com.ecoomerce.ecommerce.model.Order;
import com.ecoomerce.ecommerce.model.OrderItems;
import com.ecoomerce.ecommerce.model.Product;
import com.ecoomerce.ecommerce.repository.OrderRepo;
import com.ecoomerce.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;
    public OrderResponse placeOrder(OrderRequest request) {

        Order order = new Order();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8);
        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.customerEmail());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now()); // return current data

        List<OrderItems> orderItems = new ArrayList<>();
        for(OrderItemRequest itemReq: request.items()){

           Product product = productRepo.findById(Long.valueOf(itemReq.productId())).orElseThrow(() -> new RuntimeException("Product not found"));
           product.setStockQuantity(product.getStockQuantity()-itemReq.quantity());
           productRepo.save(product);

           OrderItems orderItem = OrderItems.builder()
                   .product(product)
                   .quantity(itemReq.quantity())
                   .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                   .order(order)
                   .build();
           orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        Order savedOrder= orderRepo.save(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for(OrderItems item : savedOrder.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice()
            );
            itemResponses.add(itemResponse);
        }

        return new OrderResponse(
                Integer.parseInt(savedOrder.getOrderId().replace("ORD", "")),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                itemResponses
        );
    }

    public List<OrderResponse> getAllOrders() {
        return null;
    }
}
