package com.tutti.server.core.payment.domain;

public enum PaymentMethodType {

    CARD, // 카드
    BANK_TRANSFER, // 계좌이체
    KAKAO_PAY, // 카카오페이
    TOSS_PAY, //토스페이
    PAYCO, // 페이코
    NAVER_PAY; // 네이버페이

    public static PaymentMethodType fromString(String value) {
        try {
            return PaymentMethodType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 결제 수단: " + value);
        }
    }
}
