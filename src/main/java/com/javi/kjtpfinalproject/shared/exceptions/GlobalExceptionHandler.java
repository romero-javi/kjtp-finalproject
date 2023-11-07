package com.javi.kjtpfinalproject.shared.exceptions;

import com.javi.kjtpfinalproject.shared.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handles no valid data
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleBindingErrors(MethodArgumentNotValidException exception) {
        Map<String, Object> errorMap = new LinkedHashMap<>();
        Map<String, Object> fields = new HashMap<>();
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", 400);
        errorMap.put("error", "The are invalid fields");
        errorMap.put("fields", fields);

        exception.getFieldErrors().forEach(
                fieldError -> fields.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(404)
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicatedResourceException.class)
    ResponseEntity<ErrorResponse> handleResourceNotFound(DuplicatedResourceException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(409)
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.CONFLICT
        );
    }
}
