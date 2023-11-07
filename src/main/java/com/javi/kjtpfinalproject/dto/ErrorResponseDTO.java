package com.javi.kjtpfinalproject.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        Integer statusCode,
        String error,
        String reason
) {
}
