package com.javi.kjtpfinalproject.dto.checkout;


import jakarta.validation.constraints.NotEmpty;

public record RemoveCheckoutDetailDTO(
        @NotEmpty(message = "You must specify the product's id to remove from the checkout")
        String productId
        ) {
}