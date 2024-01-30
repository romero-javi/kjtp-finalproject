package com.javi.kjtpfinalproject.dto.payment;

import jakarta.validation.constraints.NotEmpty;

public record CheckoutPaymentMethodRequestDTO(
        @NotEmpty(message = "You must specify the ID of one of the user payment methods")
        String paymentMethodId
) {
}
