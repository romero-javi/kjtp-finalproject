package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}