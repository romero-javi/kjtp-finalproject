package com.javi.kjtpfinalproject.product.repository;

import com.javi.kjtpfinalproject.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
