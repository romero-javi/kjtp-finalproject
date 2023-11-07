package com.javi.kjtpfinalproject.dto;

import com.javi.kjtpfinalproject.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CheckoutResponseDTO(
        UUID checkoutId,
        BigDecimal total,
        List<CheckoutDetailResponseDTO> details,
        CheckoutUserDTO user,
        PaymentMethod selectedPaymentMethod,
        AddressDTO selectedAddress
) {
}
