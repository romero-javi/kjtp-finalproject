package com.javi.kjtpfinalproject.dto.checkout;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutDetailResponseDTO(
        UUID checkoutDetailId,
        ProductDTO product,
        Integer quantityToBuy,
        BigDecimal subtotal
        ) {
}