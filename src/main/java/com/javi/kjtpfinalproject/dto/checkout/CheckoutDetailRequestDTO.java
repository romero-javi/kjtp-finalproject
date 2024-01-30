package com.javi.kjtpfinalproject.dto.checkout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CheckoutDetailRequestDTO(
        @NotEmpty(message = "You must specify the product's id to buy")
//        @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")
        String productId,

        @NotNull(message = "You must specify the quantity you want to buy")
        @Min(value = 1, message = "Minimum quantity to buy is 1")
        Integer quantityToBuy
        ) {
}