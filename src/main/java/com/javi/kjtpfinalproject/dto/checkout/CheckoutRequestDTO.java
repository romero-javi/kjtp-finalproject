package com.javi.kjtpfinalproject.dto.checkout;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CheckoutRequestDTO(
        @NotNull(message = "The list that contains the details about the checkout cannot be null")
        @Size(min = 1, message = "It needs to contain at least one product to start a checkout and its quantity to buy")
        @Valid
        List<CheckoutDetailRequestDTO> details,
        @Nullable
        String addressId,
        @Nullable
        String paymentMethodId
) {
}
