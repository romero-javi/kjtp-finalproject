package com.javi.kjtpfinalproject.dto.checkout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateCheckoutDetailDTO(
        @NotEmpty(message = "You must specify the product's id to update the quantity to buy")
        String productId,

        @NotNull(message = "You must specify the new quantity to buy")
        @Min(value = 1, message = "Minimum quantity to buy is 1")
        Integer quantityToBuy
        ) {
}