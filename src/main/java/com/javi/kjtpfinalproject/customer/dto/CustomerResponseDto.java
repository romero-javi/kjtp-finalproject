package com.javi.kjtpfinalproject.customer.dto;

import com.javi.kjtpfinalproject.address.dto.AddressResponseDto;
import com.javi.kjtpfinalproject.address.entities.CustomerAddress;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CustomerResponseDto(
        UUID customerId,
        String email,
        String firstName,
        String lastName,
        List<AddressResponseDto> addresses
) {
}
