package com.shopflow.authservice.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(AuthException ex) {
        log.warn("Auth error: {}", ex.getMessage());
        Map<String, Object> b = new HashMap<>();
        b.put("timestamp", LocalDateTime.now().toString());
        b.put("status", 401); b.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(b);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) errors.put(e.getField(), e.getDefaultMessage());
        Map<String, Object> b = new HashMap<>();
        b.put("timestamp", LocalDateTime.now().toString());
        b.put("status", 400); b.put("details", errors);
        return ResponseEntity.badRequest().body(b);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Error: {}", ex.getMessage());
        Map<String, Object> b = new HashMap<>();
        b.put("timestamp", LocalDateTime.now().toString());
        b.put("status", 500); b.put("message", "Error interno");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(b);
    }
}
