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
import java.util.List;

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
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = buildResponse(
                HttpStatus.BAD_REQUEST, "Validation failed", request, ErrorCode.VALIDATION_FAILED);

        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ValidationError(
                        e.getField(),
                        e.getDefaultMessage()))
                .toList();

        log.warn("Validation failed. path={}", request.getRequestURI());
        return ResponseEntity.badRequest()
                .body(new ValidationErrorResponse(errorResponse, validationErrors));
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

        if (cause instanceof InvalidFormatException invalidFormatException) {
            message = String.format("Invalid value '%s'", invalidFormatException.getValue());
        }

        log.warn("Invalid value. path={}", request.getRequestURI());
        return ResponseEntity.badRequest()
                .body(buildResponse(HttpStatus.BAD_REQUEST, message, request, ErrorCode.BAD_REQUEST));
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

    private ErrorResponse buildResponse(HttpStatus status, String message, HttpServletRequest request, ErrorCode code) {
        return new ErrorResponse(LocalDateTime.now(), status.value(), message, request.getRequestURI(), code);
    }
}
