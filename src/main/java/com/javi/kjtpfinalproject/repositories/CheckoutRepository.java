package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
}
