package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;

public interface FaqSearchListService {

    FaqListResponse searchFaqs(FaqSearchRequest request); // FaqSearchRequest를 받아서 처리
}
