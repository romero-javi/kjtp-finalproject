package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.CheckoutDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckouDetailRepository extends JpaRepository<CheckoutDetail, UUID> {
}
