package com.tutti.server.core.payment.domain;

public enum PaymentStatus {

    READY,            // 초기 상태 (결제 생성됨)
    IN_PROGRESS,      // 결제 인증 완료 (승인 대기 중)
    WAITING_FOR_DEPOSIT, // 가상계좌 결제 대기 상태
    DONE,             // 결제 승인 완료
    CANCELED,         // 전체 결제 취소됨
    PARTIAL_CANCELED, // 부분 취소됨
    ABORTED,          // 결제 승인 실패
    EXPIRED;          // 결제 만료됨
}
