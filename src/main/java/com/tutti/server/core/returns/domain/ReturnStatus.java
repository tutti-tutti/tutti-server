package com.tutti.server.core.returns.domain;

public enum ReturnStatus {

    RETURN_REQUESTED,      // 반품 신청
    RETURN_PROCESSING,     // 반품 처리 중 (상품 수거, 확인 등)
    RETURN_COMPLETED,      // 반품 완료 (반품 처리 완료, 환불 가능)
}
