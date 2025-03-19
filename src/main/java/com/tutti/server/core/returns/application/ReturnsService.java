package com.tutti.server.core.returns.application;

import com.tutti.server.core.returns.payload.ReturnsRequest;

public interface ReturnsService {

    void processReturnsRequest(ReturnsRequest request);

}
