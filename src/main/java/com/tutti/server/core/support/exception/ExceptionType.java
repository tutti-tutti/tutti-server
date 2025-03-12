package com.tutti.server.core.support.exception;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import static com.tutti.server.core.support.exception.ExceptionCode.E500;
import static org.springframework.boot.logging.LogLevel.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public enum ExceptionType {

    DEFAULT_ERROR(INTERNAL_SERVER_ERROR, E500, "알 수 없는 이유로 서버에서 요청을 처리할 수 없습니다.", ERROR),

    ORDER_NOT_FOUND(ExceptionCode.P01, "해당 주문을 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(ExceptionCode.P02, "결제 금액과 주문 금액이 일치하지 않습니다."),
    PAYMENT_ALREADY_COMPLETED(ExceptionCode.P03, "이미 결제가 완료된 주문입니다."),
    PAYMENT_ALREADY_PROCESSING(ExceptionCode.P04, "이미 결제가 진행 중인 주문입니다.");

    private final HttpStatus status;

    private final ExceptionCode code;

    private final String message;

    private final LogLevel logLevel;

    ExceptionType(ExceptionCode code, String message) {
        this.status = HttpStatus.BAD_REQUEST;
        this.code = code;
        this.message = message;
        this.logLevel = LogLevel.INFO;
    }

    ExceptionType(HttpStatus status, ExceptionCode code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

}
