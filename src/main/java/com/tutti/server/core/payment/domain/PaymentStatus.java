package com.tutti.server.core.payment.domain;

public enum PaymentStatus {

    PENDING,        // 결제 대기 (결제 요청 전)
    REQUESTED,      // 결제 요청 (PG사에 결제 요청)
    APPROVED,       // 결제 승인 (PG사에서 결제 완료 응답)
    FAILED,         // 결제 실패 (PG사 결제 실패 응답)
    CANCELED,       // 결제 취소 (PG사에서 취소 완료 응답)
    COMPLETED       // 결제 완료 (PG사에서 결제 완료 응답)
}
