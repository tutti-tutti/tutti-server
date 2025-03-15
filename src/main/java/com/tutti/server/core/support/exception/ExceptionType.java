package com.tutti.server.core.support.exception;

import static com.tutti.server.core.support.exception.ExceptionCode.E500;
import static org.springframework.boot.logging.LogLevel.ERROR;
import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    DEFAULT_ERROR(INTERNAL_SERVER_ERROR, E500, "알 수 없는 이유로 서버에서 요청을 처리할 수 없습니다.", ERROR),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, ExceptionCode.E401, "권한이 없습니다", INFO),

    // - 회원 -
    DUPLICATE_NICKNAME(ExceptionCode.A01, "중복된 닉네임으로 회원가입했습니다."),
    MEMBER_NOT_FOUND(ExceptionCode.A02, "회원이 존재하지 않습니다."),
    DUPLICATE_EMAIL(ExceptionCode.A03, "중복된 이메일로 회원가입했습니다."),
    LOGIN_FAIL(ExceptionCode.A04, "존재하지 않는 아이디 혹은 이메일입니다."),

    ACCOUNT_LOCKED(ExceptionCode.A05, "현재 계정이 잠금 상태입니다"),
    REQUIRE_EMAIL_AUTHENTICATION(ExceptionCode.A06, "이메일 인증 전입니다. 이메일 인증을 해주세요"),
    REQUIRE_UNLOCK_DORMANT(ExceptionCode.A07, "현재 계정이 휴면상태입니다."),

    INVALID_VERIFICATION_CODE(ExceptionCode.A08, "유효하지 않은 인증 코드입니다."),

    // - 상품 -
    PRODUCT_NOT_FOUND(ExceptionCode.B01, "존재하지 않는 상품입니다."),
    PRODUCT_QNA_NOT_FOUND(ExceptionCode.B02, "존재하지 않는 QnA 입니다."),
    PRODUCT_REVIEW_NOT_FOUND(ExceptionCode.B03, "존재하지 않는 리뷰 입니다."),
    CATEGORY_NOT_FOUND(ExceptionCode.B04, "존재하지 않는 카테고리입니다."),

    // - 장바구니 -
    CART_ITEM_NOT_FOUND(ExceptionCode.C01, "존재하지 않는 상품입니다."),

    // - 주문 -
    ORDER_NOT_FOUND(ExceptionCode.D01, "해당 주문을 찾을 수 없습니다."),
    ORDER_ITEM_NOT_FOUND(ExceptionCode.D02, "해당 주문 상품을 찾을 수 없습니다."),

    // - 결제 -
    PAYMENT_NOT_FOUND(ExceptionCode.P01, "해당 결제 내역을 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(ExceptionCode.P02, "결제 금액과 주문 금액이 일치하지 않습니다."),
    PAYMENT_ALREADY_COMPLETED(ExceptionCode.P03, "이미 결제가 완료된 주문입니다."),
    PAYMENT_ALREADY_PROCESSING(ExceptionCode.P04, "이미 결제가 진행 중인 주문입니다."),

    // - FAQ -
    FAQ_NOT_FOUND(ExceptionCode.F01, "존재하지 않는 FAQ 입니다."),
    FAQ_CATEGORY_NOT_FOUND(ExceptionCode.F02, "존재하지 않는 카테고리입니다.");

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
