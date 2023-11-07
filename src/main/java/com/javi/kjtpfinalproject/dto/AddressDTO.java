package com.javi.kjtpfinalproject.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddressDTO(
        @Nullable
        String addressId,
        @NotEmpty(message = "Street cannot be null nor blank")
        String street,
        @NotEmpty(message = "City cannot be null nor blank")
        String city,
        @NotEmpty(message = "Postal code cannot be null nor blank")
        String postalCode,
        @NotEmpty(message = "Country cannot be null nor blank")
        String country,
        @Nullable
        Long version,
        @Nullable
        LocalDateTime createdDate,
        @Nullable
        LocalDateTime lastModifiedDate
) {
}
