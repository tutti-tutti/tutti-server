package com.tutti.server.core.support.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {

    // 유효성 검증 실패 예외 처리 (@Valid, @Pattern 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        // 모든 필드의 에러 메시지를 JSON으로 변환
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 인증 코드 오류 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> handleCoreException(DomainException e) {
        switch (e.getExceptionType().getLogLevel()) {
            case ERROR -> log.error("domainException : {}", e.getMessage(), e);
            case WARN -> log.warn("domainException : {}", e.getMessage(), e);
            default -> log.info("domainException : {}", e.getMessage(), e);
        }
        return ResponseEntity.status(e.getExceptionType().getStatus())
                .body(ExceptionResponse.from(e.getExceptionType(), e.getData()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity.status(500).body(ExceptionResponse.from(ExceptionType.DEFAULT_ERROR));
    }

}
