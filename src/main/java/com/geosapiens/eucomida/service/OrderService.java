package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    
    Order createOrder(Order order);

    Optional<Order> getOrderById(UUID orderId);

    List<Order> getOrdersByUserId(UUID userId);

    List<Order> getOrdersByStatus(OrderStatus status);
}
