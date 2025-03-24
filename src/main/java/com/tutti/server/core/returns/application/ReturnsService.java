package com.tutti.server.core.returns.application;

import com.tutti.server.core.returns.payload.ReturnsRequest;
import com.tutti.server.core.returns.payload.ReturnsResponse;

public interface ReturnsService {

    void processReturnsRequest(ReturnsRequest request, Long authMemberId);

    ReturnsResponse getReturnsByOrderId(Long orderId, Long authMemberId);

}
