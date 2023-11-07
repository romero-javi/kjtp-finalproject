package com.javi.kjtpfinalproject.shared.exceptions;

import com.javi.kjtpfinalproject.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handles no valid fields
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleBindingErrors(MethodArgumentNotValidException exception) {
        Map<String, Object> errorMap = new LinkedHashMap<>();
        Map<String, Object> fields = new HashMap<>();
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", 400);
        errorMap.put("error", "Invalid data");
        errorMap.put("reason", "You have invalid fields in your request body");
        errorMap.put("fields", fields);

        exception.getFieldErrors().forEach(
                fieldError -> fields.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponseDTO> handleResourceNotFound(NotFoundException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(404)
                        .error("Not found")
                        .reason(exception.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicatedResourceException.class)
    ResponseEntity<ErrorResponseDTO> handleDuplicatedResource(DuplicatedResourceException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(409)
                        .error("Duplicate resource")
                        .reason(exception.getMessage())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(InvalidDataException.class)
    ResponseEntity<ErrorResponseDTO> handleInvalidData(InvalidDataException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(400)
                        .error("Invalid data")
                        .reason(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotSupportedException.class)
    ResponseEntity<ErrorResponseDTO> handleNoSupported(NotSupportedException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(400)
                        .error("Not Supported")
                        .reason(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
