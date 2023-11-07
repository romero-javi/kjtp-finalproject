package com.javi.kjtpfinalproject.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutDetailResponseDTO(
        UUID checkoutDetailId,
        ProductDTO product,
        Integer quantityToBuy,
        BigDecimal subtotal
        ) {
}