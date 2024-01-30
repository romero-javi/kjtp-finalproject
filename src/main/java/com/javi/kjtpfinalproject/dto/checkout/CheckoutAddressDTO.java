package com.javi.kjtpfinalproject.dto.checkout;

import jakarta.validation.constraints.NotEmpty;

public record CheckoutAddressDTO(
        @NotEmpty(message = "You must specify the ID of one of the user addresses for the checkout")
        String addressId
) {
}
