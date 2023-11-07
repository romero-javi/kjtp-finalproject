package com.javi.kjtpfinalproject.customer.repository;

import com.javi.kjtpfinalproject.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}