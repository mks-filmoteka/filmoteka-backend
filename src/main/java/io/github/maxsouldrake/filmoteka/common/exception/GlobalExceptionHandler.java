package io.github.maxsouldrake.filmoteka.common.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        log.warn("Resource not found. path={}, message={}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, ErrorCode.NOT_FOUND));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException ex, HttpServletRequest request) {

        log.warn("Conflict. path={}, message={}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request, ErrorCode.CONFLICT));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorDetail> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErrorDetail(
                        e.getField(),
                        e.getDefaultMessage()))
                .toList();

        ErrorResponse errorResponse = buildResponse(
                HttpStatus.BAD_REQUEST, "Validation failed", request, ErrorCode.VALIDATION_FAILED, details);

        log.warn("Validation failed. path={}", request.getRequestURI());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());

        log.warn("Invalid argument type. path={}", request.getRequestURI());
        return ResponseEntity.badRequest()
                .body(buildResponse(HttpStatus.BAD_REQUEST, message, request, ErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        String message = ex.getMessage();
        Throwable cause = ex.getMostSpecificCause();
        List<ErrorDetail> errorDetails = new ArrayList<>();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            message = "Invalid value '%s'".formatted(invalidFormatException.getValue());
            if (invalidFormatException.getTargetType().isEnum()) {
                String allowedValues = Arrays.stream(invalidFormatException.getTargetType().getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                errorDetails.add(new ErrorDetail("genres", "Allowed values are: " + allowedValues));
            }
        }

        log.warn("Invalid value. path={}", request.getRequestURI());
        return ResponseEntity.badRequest()
                .body(buildResponse(HttpStatus.BAD_REQUEST, message, request, ErrorCode.BAD_REQUEST, errorDetails));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected error. path={}", request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getCause().getMessage(),
                        request,
                        ErrorCode.INTERNAL_ERROR));
    }

    private ErrorResponse buildResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            ErrorCode code,
            List<ErrorDetail> errorDetails) {

        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getRequestURI(),
                code,
                errorDetails);
    }

    private ErrorResponse buildResponse(
            HttpStatus status, String message, HttpServletRequest request, ErrorCode code) {

        return new ErrorResponse(
                LocalDateTime.now(), status.value(), message, request.getRequestURI(), code, List.of());
    }
}
