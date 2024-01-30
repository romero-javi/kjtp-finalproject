package com.javi.kjtpfinalproject.dto.order;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDetailDTO(
        UUID orderDetailId,
        ProductDTO product,
        Integer quantityToBuy,
        BigDecimal subtotal
        ) {
}