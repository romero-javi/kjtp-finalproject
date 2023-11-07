package com.javi.kjtpfinalproject.address.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AddressUpdateDto(
        @NotEmpty(message = "Street cannot be null nor blank")
        String street,
        @NotEmpty(message = "City cannot be null nor blank")
        String city,
        @NotEmpty(message = "Postal code cannot be null nor blank")
        String postalCode,
        @NotEmpty(message = "Country cannot be null nor blank")
        String country
) {
}
