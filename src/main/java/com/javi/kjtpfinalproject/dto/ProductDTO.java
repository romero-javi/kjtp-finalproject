package com.javi.kjtpfinalproject.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDTO(
        @Nullable
        UUID productId,
        @NotEmpty(message = "Product name cannot be null nor blank")
        String name,
        @NotEmpty(message = "Product brand cannot be null nor blank")
        String brand,
        @Min(value = 0, message = "You cannot enter a negative quantity")
        @NotNull(message = "Quantity cannot be null")
        Integer quantity,
        @NotNull
        @Min(value = 0)
        BigDecimal price,
        @Nullable
        Long version,
        @Nullable
        LocalDateTime createdDate,
        @Nullable
        LocalDateTime lastModifiedDate
) {
}
