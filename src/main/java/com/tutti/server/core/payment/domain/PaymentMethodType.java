package com.tutti.server.core.payment.domain;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Arrays;
import lombok.Getter;

@Getter
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

    // 한글 문자열을 Enum으로 변환하는 메서드
    public static PaymentMethodType fromString(String value) {
        return Arrays.stream(PaymentMethodType.values())
                .filter(type -> type.displayName.equals(value))
                .findFirst()
                .orElseThrow(() -> new DomainException(ExceptionType.INVALID_METHOD));
    }
}
