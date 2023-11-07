package com.javi.kjtpfinalproject.shared.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer statusCode,
        String error
) {
}
