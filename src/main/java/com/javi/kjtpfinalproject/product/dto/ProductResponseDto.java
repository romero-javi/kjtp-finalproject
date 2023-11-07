package com.javi.kjtpfinalproject.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDto (
        UUID productId,
        String name,
        String brand,
        Integer availableQuantity,
        BigDecimal price
) {
}
