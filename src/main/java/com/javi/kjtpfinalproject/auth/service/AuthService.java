package com.javi.kjtpfinalproject.auth.service;

import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;

public interface AuthService {
    String createUser(CustomerRegistrationDto newUser);
    void deleteUser(String userId);
    void updateUser(String userId, CustomerRegistrationDto user);
}
