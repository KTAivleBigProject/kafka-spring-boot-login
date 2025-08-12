package com.example.demo.global;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Bean Validation 에러: 필드별 다중 메시지 지원
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = (error instanceof FieldError) ? ((FieldError) error).getField() : "global";
            String message = error.getDefaultMessage();

            errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        log.warn("⚠️ RuntimeException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e) {
        log.warn("🔒 Expired JWT Token");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "토큰이 만료되었습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("❗예상치 못한 오류: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "서버 내부 오류가 발생했습니다."));
    }
}
