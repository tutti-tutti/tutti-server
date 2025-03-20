package com.tutti.server.core.refund.payload;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.refund.domain.Refund;
import com.tutti.server.core.refund.domain.RefundStatus;
import java.time.LocalDateTime;

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
        return new RefundViewResponse(
                refund.getId(),
                refund.getPayment().getId(),
                refund.getAmount(),
                refund.getRefundStatus(),
                refund.getRefundMethod(),
                refund.getReturnFee(),
                refund.getUpdatedAt()
        );
    }
}
