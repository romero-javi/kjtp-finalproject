package com.javi.kjtpfinalproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CheckoutDetailRequestDTO(
        @NotEmpty(message = "You need to specify the product's id to buy")
        String productId,

        @NotNull(message = "You need to specify the quantity you want to but")
        @Min(value = 1, message = "Minimum quantity to buy is 1")
        Integer quantityToBuy
        ) {
}