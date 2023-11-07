package com.javi.kjtpfinalproject.address.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressResponseDto(
        UUID addressId,
        String street,
        String city,
        String postalCode,
        String country
) {
}
