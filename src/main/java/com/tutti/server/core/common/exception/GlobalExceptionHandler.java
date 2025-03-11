package com.tutti.server.core.common.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode().name(),
                ex.getStatus().value(),
                ex.getErrorMessage()
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

//    // @Controller의 @Valid를 통한 예외를 처리해주는 핸들러.
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(
//            MethodArgumentNotValidException exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//        String errorMessages = bindingResult.getFieldErrors().stream()
//                .map(error -> error.getField() + " : " + error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR",
//                HttpStatus.BAD_REQUEST.value(), errorMessages);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }

    // 예상치 못한 예외 처리 (500 응답)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR.name(),
                500,
                "서버 오류가 발생했습니다. 다시 시도해주세요."
        );
        return ResponseEntity.status(500).body(errorResponse);
    }

//    // 기타 예외 처리 (500 Internal Server Error 방지)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Collections.singletonMap("error", "서버 오류가 발생했습니다. 다시 시도해주세요."));
//    }

}
