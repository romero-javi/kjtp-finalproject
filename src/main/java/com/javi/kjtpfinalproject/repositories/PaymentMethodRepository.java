package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
}
