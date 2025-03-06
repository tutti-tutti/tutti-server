package com.tutti.server.core.payment.domain;

public enum PaymentStatus {

    PENDING, // 결제 대기 중
    IN_PROGRESS, //결제 진행 중(승인 요청 등)
    COMPLETED, // 결제 완료
    CANCELED, // 결제 취소
    FAILED, // 결제 실패
}
