package com.tutti.server.core.refund.payload;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.refund.domain.Refund;
import com.tutti.server.core.refund.domain.RefundStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RefundViewResponse(
        Long refundId,
        Long paymentId,
        int amount,
        RefundStatus refundStatus,
        PaymentMethodType refundMethod,
        int returnFee,
        LocalDateTime refundUpdatedAt
) {

    public static RefundViewResponse fromEntity(Refund refund) {
        return RefundViewResponse.builder()
                .refundId(refund.getId())
                .paymentId(refund.getPayment().getId())
                .amount(refund.getAmount())
                .refundStatus(refund.getRefundStatus())
                .refundMethod(refund.getRefundMethod())
                .returnFee(refund.getReturnFee())
                .refundUpdatedAt(refund.getUpdatedAt())
                .build();
    }
}
