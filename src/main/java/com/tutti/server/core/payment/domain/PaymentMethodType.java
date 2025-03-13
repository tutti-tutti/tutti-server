package com.tutti.server.core.payment.domain;

import java.util.Arrays;

public enum PaymentMethodType {

    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    EASY_PAY("간편결제"),
    MOBILE("휴대폰"),
    BANK_TRANSFER("계좌이체"),
    CULTURE_GIFT("문화상품권"),
    BOOK_GIFT("도서문화상품권"),
    GAME_GIFT("게임문화상품권");

    private final String displayName;

    PaymentMethodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PaymentMethodType fromString(String value) {
        return Arrays.stream(PaymentMethodType.values())
                .filter(type -> type.displayName.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 결제 수단: " + value));
    }
}
