package com.tutti.server.core.refund.api;

import com.tutti.server.core.refund.payload.RefundRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refund")
public class RefundApi implements RefundApiSpec {

    @PostMapping("/request")
    public void requestRefund(@Valid RefundRequest request) {

    }


}
