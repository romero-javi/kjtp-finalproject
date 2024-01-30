package com.javi.kjtpfinalproject.services.impl;

import com.javi.kjtpfinalproject.entities.UserAddress;
import com.javi.kjtpfinalproject.repositories.UserAddressRepository;
import com.javi.kjtpfinalproject.services.UserAddressService;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAddressServiceJPA implements UserAddressService {
    private final UserAddressRepository userAddressRepository;

    @Override
    public UserAddress findUserAddress(String addressId) {
        return userAddressRepository.findById(UUID.fromString(addressId))
                .orElseThrow(
                        () -> new NotFoundException("User's address with id '%s' not found".formatted(addressId))
                );
    }
}
