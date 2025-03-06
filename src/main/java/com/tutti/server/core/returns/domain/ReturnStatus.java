package com.tutti.server.core.returns.domain;

public enum ReturnStatus {
    
    REQUESTED,      // 반품 신청
    APPROVED,       // 반품 승인 (판매자/관리자 승인)
    PROCESSING,     // 반품 처리 중 (상품 수거, 확인 등)
    COMPLETED,      // 반품 완료 (반품 처리 완료, 환불 가능)
    REJECTED        // 반품 거절 (반품 불가 사유 존재)
}
