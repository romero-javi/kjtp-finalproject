package com.javi.kjtpfinalproject.dto;

import com.javi.kjtpfinalproject.entities.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record UserDTO(
        @Nullable
        UUID userId,
        @NotEmpty(message = "Username cannot be null nor blank")
        @Email(message = "Enter a valid email, e.g: example@applaudostudios.org")
        String email,
        @NotEmpty(message = "First name cannot be null nor blank")
        String firstName,
        @NotEmpty(message = "Last name cannot be null nor blank")
        String lastName,
        @Size(min = 1, message = "You need at least one address")
        List<AddressDTO> addresses,
        List<PaymentMethod> paymentMethods,
        @Nullable
        Long version,
        @Nullable
        LocalDateTime createdDate,
        @Nullable
        LocalDateTime lastModifiedDate
) {
}
