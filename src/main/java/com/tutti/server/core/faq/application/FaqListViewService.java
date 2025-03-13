package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;

public interface FaqListViewService {

    /**
     * FAQ 목록을 조회합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 요청 조건에 맞는 FAQ 목록
     */
    FaqListResponse getFaqs(FaqListRequest request);
}
