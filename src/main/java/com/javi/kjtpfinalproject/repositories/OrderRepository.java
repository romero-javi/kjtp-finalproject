package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.Order;
import com.javi.kjtpfinalproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> getOrderByUser(User user);
}
