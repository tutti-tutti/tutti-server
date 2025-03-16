package com.tutti.server.core.support.exception;

import static org.springframework.boot.logging.LogLevel.ERROR;
import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    DEFAULT_ERROR(INTERNAL_SERVER_ERROR, ExceptionCode.E500, "알 수 없는 이유로 서버에서 요청을 처리할 수 없습니다.",
        ERROR),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, ExceptionCode.E401, "권한이 없습니다", INFO),

    // - 회원 인증 관련 -
    INVALID_EMAIL_FORMAT(ExceptionCode.A01, "올바른 이메일 주소를 입력해주세요."),
    EMAIL_ALREADY_VERIFIED(ExceptionCode.A02, "이미 인증된 이메일입니다."),
    INVALID_VERIFICATION_CODE(ExceptionCode.A03, "인증 코드가 올바르지 않거나 만료되었습니다."),

    // - 회원 가입 관련 -
    PASSWORD_MISMATCH(ExceptionCode.A11, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    MISSING_REQUIRED_FIELD(ExceptionCode.A12, "필수 입력 항목은(는) 필수 입력 항목입니다."),
    REQUIRED_TERMS_NOT_AGREED(ExceptionCode.A13, "필수 약관(서비스 이용약관)에 동의해야 합니다."),
    PASSWORD_TOO_SHORT(ExceptionCode.A14, "비밀번호는 최소 8자 이상이어야 합니다."),
    PASSWORD_COMPLEXITY_NOT_MET(ExceptionCode.A15, "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."),
    EMAIL_ALREADY_EXISTS(ExceptionCode.A16, "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(ExceptionCode.A17, "이메일 인증을 완료해야 회원가입이 가능합니다."),
    TERMS_NOT_FOUND(ExceptionCode.A18, "약관 정보를 찾을 수 없습니다."),

    // - 로그인 관련 예외 -
    MEMBER_NOT_FOUND(ExceptionCode.A21, "이메일이 존재하지 않습니다."),
    ACCOUNT_WITHDRAWN(ExceptionCode.A22, "탈퇴한 계정입니다."),
    ACCOUNT_BANNED(ExceptionCode.A23, "정지된 계정입니다."),
    LOGIN_PASSWORD_MISMATCH(ExceptionCode.A24, "비밀번호가 일치하지 않습니다."),

    // - 비밀번호 기능 관련 예외 -
    INVALID_PASSWORD_FORMAT(ExceptionCode.A30, "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
    PASSWORD_SAME_AS_OLD(ExceptionCode.A31, "새로운 비밀번호는 기존 비밀번호와 다르게 설정해야 합니다."),

    // - JWT 관련 예외 _
    TOKEN_EXPIRED(ExceptionCode.A41, "토큰이 만료되었습니다."),
    INVALID_JWT_TOKEN(ExceptionCode.A42, "유효하지 않은 JWT 토큰입니다."),
    INVALID_JWT_SIGNATURE(ExceptionCode.A43, "JWT 서명이 유효하지 않습니다."),
    JWT_CREATION_FAILED(ExceptionCode.A44, "JWT 토큰 생성에 실패했습니다."),

    // - 상품 -
    PRODUCT_NOT_FOUND(ExceptionCode.B01, "존재하지 않는 상품입니다."),
    PRODUCT_QNA_NOT_FOUND(ExceptionCode.B02, "존재하지 않는 QnA 입니다."),
    PRODUCT_REVIEW_NOT_FOUND(ExceptionCode.B03, "존재하지 않는 리뷰 입니다."),
    CATEGORY_NOT_FOUND(ExceptionCode.B04, "존재하지 않는 카테고리입니다."),

    // - 장바구니 -
    CART_ITEM_NOT_FOUND(ExceptionCode.C01, "존재하지 않는 상품입니다."),

    // - 주문 -
    ORDER_NOT_FOUND(ExceptionCode.D01, "해당 주문을 찾을 수 없습니다."),

    // - 결제 -
    PAYMENT_NOT_FOUND(ExceptionCode.P01, "해당 결제 내역을 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(ExceptionCode.P02, "결제 금액과 주문 금액이 일치하지 않습니다."),
    PAYMENT_ALREADY_COMPLETED(ExceptionCode.P03, "이미 결제가 완료된 주문입니다."),
    PAYMENT_ALREADY_PROCESSING(ExceptionCode.P04, "이미 결제가 진행 중인 주문입니다."),

    // - FAQ -
    FAQ_NOT_FOUND(ExceptionCode.F01, "존재하지 않는 FAQ 입니다."),
    FAQ_CATEGORY_NOT_FOUND(ExceptionCode.F02, "존재하지 않는 카테고리입니다."),
    FAQ_FEEDBACK_FAILED(ExceptionCode.F03, "피드백 등록에 실패했습니다."),

    // - 상품 리뷰 -
    REVIEW_NOT_FOUND(ExceptionCode.R01, "존재하지 않는 리뷰입니다.");

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
