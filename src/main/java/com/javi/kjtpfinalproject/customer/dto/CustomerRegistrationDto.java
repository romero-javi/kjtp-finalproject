package com.javi.kjtpfinalproject.customer.dto;

import com.javi.kjtpfinalproject.address.dto.AddressRegistrationDto;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record CustomerRegistrationDto(
        @NotEmpty(message = "Username cannot be null nor blank")
        @Email(message = "Enter a valid email, e.g: example@applaudostudios.org")
        String email,
        @NotEmpty(message = "First name cannot be null nor blank")
        String firstName,
        @NotEmpty(message = "Last name cannot be null nor blank")
        String lastName,
        @NotEmpty(message = "Password cannot be null nor blank")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "The password must follow the following criteria: at least 8 characters, " +
                        "at least one uppercase letter, at least one lowercase letter, at least one digit and " +
                        "at least one special character."
        )
        String password,
        @Size(min = 1, message = "You need at least one address")
        List<AddressRegistrationDto> addresses
) {
}
