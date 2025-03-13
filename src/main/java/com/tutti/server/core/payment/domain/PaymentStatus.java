package com.tutti.server.core.payment.domain;

public enum PaymentStatus {

    PAYMENT_REQUESTED,      // 결제 요청 (PG사에 결제 요청)
    PAYMENT_APPROVED,       // 결제 승인 (PG사에서 결제 완료 응답)
    PAYMENT_FAILED,         // 결제 실패 (PG사 결제 실패 응답)
    PAYMENT_CANCELED,       // 결제 취소 (PG사에서 취소 완료 응답)
    PAYMENT_COMPLETED;       // 결제 완료 (PG사에서 결제 완료 응답)
}
