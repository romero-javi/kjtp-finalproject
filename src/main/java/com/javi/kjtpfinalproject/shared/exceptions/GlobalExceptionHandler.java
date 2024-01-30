package com.javi.kjtpfinalproject.shared.exceptions;

import com.javi.kjtpfinalproject.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String INVALID_DATA_ERROR = "Invalid Data";

    // Handles not valid JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorResponseDTO> handleJsonParseException(HttpMessageNotReadableException ex) {
        log.error("Error parsing JSON: {}", ex.getMessage());

        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Invalid JSON")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handles invalid payload fields
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handleBindingErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errorMap = new LinkedHashMap<>();
        Map<String, Object> fields = new HashMap<>();
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", ex.getStatusCode().value());
        errorMap.put("error", INVALID_DATA_ERROR);
        errorMap.put("reason", "You have invalid fields in your request body");
        errorMap.put("fields", fields);

        ex.getFieldErrors().forEach(
                fieldError -> fields.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errorMap);
    }

    // Handles data integrity violation in entities
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(INVALID_DATA_ERROR)
                        .reason(Objects.requireNonNull(ex.getRootCause()).getMessage())
                        .build()
        );
    }

    // Handles not found resources
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleResourceNotFound(NotFoundException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .error("Not found")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    // Handles duplicated resources
    @ExceptionHandler(DuplicatedResourceException.class)
    public final ResponseEntity<ErrorResponseDTO> handleDuplicatedResource(DuplicatedResourceException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.CONFLICT.value())
                        .error("Duplicate resource")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    // Handles invalid data
    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<ErrorResponseDTO> handleInvalidData(InvalidDataException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(INVALID_DATA_ERROR)
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handles insufficient amount to make an operation
    @ExceptionHandler(InsufficientAmountException.class)
    public final ResponseEntity<ErrorResponseDTO> handleInsufficientAmount(InsufficientAmountException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Insufficient amount")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handles insufficient funds to process payments
    @ExceptionHandler(InsufficientFundsException.class)
    public final ResponseEntity<ErrorResponseDTO> handleInsufficientFunds(InsufficientFundsException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.PAYMENT_REQUIRED.value())
                        .error("Insufficient funds")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.PAYMENT_REQUIRED
        );
    }

    // Handles not supported requested data
    @ExceptionHandler(NotSupportedException.class)
    public final ResponseEntity<ErrorResponseDTO> handleNoSupported(NotSupportedException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())

                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Not Supported")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorResponseDTO> handleTypeMismatching(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Incorrect Type")
                        .reason(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
