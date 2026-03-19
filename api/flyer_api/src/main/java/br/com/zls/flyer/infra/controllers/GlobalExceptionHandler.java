package br.com.zls.flyer.infra.controllers;

import br.com.zls.flyer.domain.exceptions.CustomAuthenticationException;
import br.com.zls.flyer.domain.exceptions.JwtAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<Map<String, String>> handleJwtException(JwtAuthException ex) {
        log.error("JWT error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "JWT Error", "message", ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(CustomAuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Authentication Error", "message", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Unauthorized", "message", "Invalid username or password"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Map<String, String>> handleDisabled(DisabledException ex) {
        log.warn("Account disabled: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of("error", "Forbidden", "message", "Account is disabled"));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Map<String, String>> handleLocked(LockedException ex) {
        log.warn("Account locked: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of("error", "Forbidden", "message", "Account is locked"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UsernameNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Unauthorized", "message", "Invalid username or password"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Internal Server Error", "message", "An unexpected error occurred"));
    }
}
