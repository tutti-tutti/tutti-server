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

    //Global
    INVALID_INPUT(ExceptionCode.G01, "유효하지 않은 입력 값입니다."),

    // - 회원 인증 관련 -
    INVALID_EMAIL_FORMAT(ExceptionCode.A01, "올바른 이메일 주소를 입력해주세요."),
    EMAIL_ALREADY_VERIFIED(ExceptionCode.A02, "이미 인증된 이메일입니다."),
    INVALID_VERIFICATION_CODE(ExceptionCode.A03, "인증 코드가 올바르지 않거나 만료되었습니다."),
    INVALID_EMAIL_VERIFICATION_TYPE(ExceptionCode.A04, "유효하지 않은 이메일 인증 목적입니다."),

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
    MISSING_AUTH_HEADER(ExceptionCode.A45, "Authorization 헤더가 필요합니다."),
    TOKEN_LOGGED_OUT(ExceptionCode.A46, "이미 로그아웃된 토큰입니다."),

    // - 소셜 관련 예외 -
    INVALID_SOCIAL_TOKEN(ExceptionCode.A51, "유효하지 않은 소셜 로그인 액세스 토큰입니다."),
    INVALID_SOCIAL_PROVIDER(ExceptionCode.A52, "지원하지 않는 소셜 로그인 제공자입니다."),
    SOCIAL_AUTH_SERVER_ERROR(ExceptionCode.A53, "소셜 인증 서버에서 오류가 발생했습니다."),
    NOT_SOCIAL_MEMBER(ExceptionCode.A54, "소셜 회원이 아닙니다."),

    // - 상품 -
    PRODUCT_NOT_FOUND(ExceptionCode.B01, "존재하지 않는 상품입니다."),
    PRODUCT_QNA_NOT_FOUND(ExceptionCode.B02, "존재하지 않는 QnA 입니다."),
    PRODUCT_REVIEW_NOT_FOUND(ExceptionCode.B03, "존재하지 않는 리뷰 입니다."),
    CATEGORY_NOT_FOUND(ExceptionCode.B04, "존재하지 않는 카테고리입니다."),
    PRODUCT_CATEGORY_MAP_NOT_FOUND(ExceptionCode.B07, "존재하지 않는 카테고리 상품입니다."),
    PRODUCT_ITEM_NOT_FOUND(ExceptionCode.B08, "상품 옵션이 존재하지 않습니다"),
    SKU_NOT_FOUND(ExceptionCode.B09, "상품에 대한 재고가 존재하지 않습니다"),

    // - 장바구니 -
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.C01, "존재하지 않는 상품입니다.", ERROR),

    // - 주문 -
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.D01, "해당 주문을 찾을 수 없습니다.", ERROR),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.D02, "해당 주문 상품을 찾을 수 없습니다.", ERROR),
    ORDER_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.D03, "해당 주문 이력을 찾을 수 없습니다.", ERROR),
    ORDER_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, ExceptionCode.D04, "구매 확정된 주문입니다.", ERROR),
    NON_EXISTENT_PRODUCT_INCLUDE(HttpStatus.BAD_REQUEST, ExceptionCode.D05,
            "존재하지 않는 상품이 포함되어 있습니다.", ERROR),
    PRODUCT_MISMATCH(HttpStatus.BAD_REQUEST, ExceptionCode.D06, "상품을 찾을 수 없습니다.", ERROR),
    DUPLICATE_PRODUCT_ITEMS(HttpStatus.BAD_REQUEST, ExceptionCode.D07, "중복된 상품 ID가 존재합니다.", ERROR),

    // - 결제 -
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.P01, "해당 결제 내역을 찾을 수 없습니다.", ERROR),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, ExceptionCode.P02, "결제 금액과 주문 금액이 일치하지 않습니다.",
            ERROR),
    PAYMENT_ALREADY_COMPLETED(HttpStatus.CONFLICT, ExceptionCode.P03, "이미 결제가 완료된 주문입니다.", ERROR),
    PAYMENT_ALREADY_PROCESSING(HttpStatus.CONFLICT, ExceptionCode.P04, "이미 결제가 진행 중인 주문입니다.",
            ERROR),
    INVALID_METHOD(HttpStatus.BAD_REQUEST, ExceptionCode.P05, "유효하지 않은 결제 수단입니다.", ERROR),
    MISSING_METHOD_NOT_FOUND(HttpStatus.BAD_REQUEST, ExceptionCode.P06, "결제 수단이 선택되지 않았습니다.",
            ERROR),
    PAYMENT_ALREADY_EXISTS(HttpStatus.CONFLICT, ExceptionCode.P07, "결제 요청이 불가능한 상태입니다.", ERROR),
    ORDER_NAME_MISMATCH(HttpStatus.BAD_REQUEST, ExceptionCode.P08, "주문명이 일치하지 않습니다.", ERROR),
    TOSS_ERROR(HttpStatus.BAD_REQUEST, ExceptionCode.T1, "토스페이먼츠가 응답하지 않습니다. 잠시 후 다시 시도해주세요.",
            ERROR),
    TOSS_NOT_RESPONSE(HttpStatus.BAD_REQUEST, ExceptionCode.T2, "토스페이먼츠에서 응답이 없습니다.", ERROR),
    TOSS_CANCEL_FAIL(HttpStatus.BAD_REQUEST, ExceptionCode.T3, "결제 취소가 실패하였습니다.", ERROR),


    // - 환불 -
    REFUND_REQUEST_NOT_ALLOWED(HttpStatus.CONFLICT, ExceptionCode.R01, "환불 및 결제취소 대상이 아닙니다.",
            ERROR),
    REFUND_REQUEST_EXPIRED(HttpStatus.BAD_REQUEST, ExceptionCode.R02, "환불 기간이 지났습니다.", ERROR),
    REFUND_ALREADY_COMPLETED(HttpStatus.CONFLICT, ExceptionCode.R03, "이미 환불이 완료된 결제입니다.", ERROR),
    REFUND_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.R07, "조회 가능한 환불정보가 없습니다.", ERROR),
    REFUND_OR_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.R08,
            "권한이 없거나 조회 가능한 환불 정보가 없습니다.", ERROR),


    // - 반품 -
    RETURNS_ALREADY_COMPLETED(HttpStatus.CONFLICT, ExceptionCode.R04, "이미 반품이 완료된 주문입니다.", ERROR),
    RETURNS_REQUEST_EXPIRED(HttpStatus.BAD_REQUEST, ExceptionCode.R05, "반품 기간이 지났습니다.", ERROR),
    RETURNS_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionCode.R06, "해당 반품 내역을 찾을 수 없습니다.", ERROR),


    // - FAQ -
    FAQ_NOT_FOUND(ExceptionCode.F01, "존재하지 않는 FAQ 입니다."),
    FAQ_CATEGORY_NOT_FOUND(ExceptionCode.F02, "존재하지 않는 카테고리입니다."),
    FAQ_FEEDBACK_FAILED(ExceptionCode.F03, "피드백 등록에 실패했습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.BAD_REQUEST, ExceptionCode.F04, "FAQ 수정에 실패했습니다.", ERROR),
    FAQ_ADMIN_ONLY(ExceptionCode.F04, "권한이 존재하지 않습니다."),

    // - 리뷰 -
    REVIEW_ALREADY_LIKED(ExceptionCode.G01, "이미 평가를 완료한 리뷰입니다."),
    REVIEW_LIKE_FAILED(ExceptionCode.G02, "리뷰 평가에 실패했습니다."),
    REVIEW_UNAUTHORIZED(ExceptionCode.G03, "리뷰에 대한 권한이 없습니다."),
    REVIEW_CONTENT_EMPTY(ExceptionCode.G04, "리뷰 내용이 비어 있습니다."),
    REVIEW_CREATE_FAILED(ExceptionCode.G05, "리뷰 등록에 실패했습니다."),
    REVIEW_DELETE_FAILED(ExceptionCode.G06, "리뷰 삭제에 실패했습니다."),
    UNAUTHORIZED_REVIEW_ACCESS(ExceptionCode.G07, "접근이 허용되지 않은 리뷰입니다."),

    // - 배송 -
    DELIVERY_NOT_FOUND(HttpStatus.BAD_REQUEST, ExceptionCode.H01, "존재하지 않는 배송입니다.", ERROR),

    // - 스토어 -
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, ExceptionCode.I01, "존재하지 않는 스토어입니다.", ERROR);

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
