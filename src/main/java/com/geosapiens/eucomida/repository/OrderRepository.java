package com.geosapiens.eucomida.repository;

import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByUserId(UUID userId);
}
