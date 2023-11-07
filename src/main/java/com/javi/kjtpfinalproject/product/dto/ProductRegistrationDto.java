package com.javi.kjtpfinalproject.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record ProductRegistrationDto (
        @NotEmpty(message = "Product name cannot be null nor blank")
        String name,
        @NotEmpty(message = "Product brand cannot be null nor blank")
        String brand,
        @NotEmpty(message = "Product quantity cannot be null nor blank")
        Integer availableQuantity,
        @NotEmpty(message = "Product price cannot be null nor blank")
        @Min(value = 0)
        BigDecimal price
) {
}
