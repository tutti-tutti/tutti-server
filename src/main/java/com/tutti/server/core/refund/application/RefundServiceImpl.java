package com.tutti.server.core.refund.application;

import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.refund.infrastructure.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentService paymentService;


}
