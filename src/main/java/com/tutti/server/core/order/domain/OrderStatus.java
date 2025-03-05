package com.tutti.server.core.order.domain;

public enum OrderStatus {

    PAYMENT_PENDING,             // 결제대기
    ORDER_CANCELED,              // 주문 취소
    PREPARING_FOR_DELIVERY,      // 발송 준비 중
    DELIVERING,                  // 배송중
    DELIVERED,                   // 배송 완료
    COMPLETED,                   // 주문 완료
    REFUND_REQUESTED,            // 환불 신청
    REFUNDING,                   // 환불 처리 중
    REFUND_COMPLETED             // 환불 완료

}
