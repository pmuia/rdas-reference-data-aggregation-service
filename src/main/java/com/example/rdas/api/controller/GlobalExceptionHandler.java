package com.example.rdas.api.controller;

import com.example.rdas.api.response.ReferenceDataResponse;
import com.example.rdas.domain.exception.ReferenceDataException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReferenceDataException.class)
    ResponseEntity<ReferenceDataResponse<Void>> handleReferenceDataException(
            ReferenceDataException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.status())
                .body(ReferenceDataResponse.error(serviceName(request), exception.code(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ReferenceDataResponse<Void>> handleValidation(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(ReferenceDataResponse.error(serviceName(request), "VALIDATION_ERROR", message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ReferenceDataResponse<Void>> handleForbidden(AccessDeniedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ReferenceDataResponse.error(serviceName(request), "FORBIDDEN", "Access is denied"));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ReferenceDataResponse<Void>> handleUnexpected(Exception exception, HttpServletRequest request) {
        return ResponseEntity.internalServerError()
                .body(ReferenceDataResponse.error(serviceName(request), "INTERNAL_SERVER_ERROR", "Unexpected server error"));
    }

    private String serviceName(HttpServletRequest request) {
        return request.getAttribute("service") instanceof String service ? service : "unknown";
    }
}
