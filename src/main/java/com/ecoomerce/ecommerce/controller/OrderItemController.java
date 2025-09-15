package com.ecoomerce.ecommerce.controller;

import com.ecoomerce.ecommerce.model.DTO.OrderRequest;
import com.ecoomerce.ecommerce.model.DTO.OrderResponse;
import com.ecoomerce.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderItemController {

    @Autowired
    private OrderService orderService ;

    @PostMapping("/order/place")
    public ResponseEntity<OrderResponse> placeOder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse =orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = orderService.getAllOrders();
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }
}
