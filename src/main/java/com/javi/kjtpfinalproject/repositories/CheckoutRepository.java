package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
    Optional<Checkout> getCheckoutByUser(User user);
}
