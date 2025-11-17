package com.example.biblioteca_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(NotFoundException ex) {
        return Map.of("erro", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrity(DataIntegrityException ex) {
        return Map.of("erro", ex.getMessage());
    }
}
