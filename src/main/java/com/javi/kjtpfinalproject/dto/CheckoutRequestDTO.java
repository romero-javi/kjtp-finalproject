package com.javi.kjtpfinalproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CheckoutRequestDTO(
        @NotNull(message = "The list that contains the details about the checkout cannot be null")
        @Size(min = 1, message = "It needs to contain at least one product to start a checkout and its quantity to buy")
        @Valid
        List<CheckoutDetailRequestDTO> details,
        @NotEmpty(message = "Set the address of the checkout, this address will be the final order's")
        String addressId,
        @NotEmpty(message = "Set the payment method of the checkout, this payment method  will be the final order's")
        String paymentMethodId
) {
}
