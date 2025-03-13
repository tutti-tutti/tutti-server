package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface FaqApiSpec {

    /**
     * FAQ 카테고리 목록을 조회합니다.
     *
     * @return 중복이 제거된 FAQ 카테고리 목록
     */
    ResponseEntity<List<String>> getCategories();

    /**
     * 조회수가 높은 상위 10개의 FAQ를 조회합니다.
     *
     * @return 조회수가 높은 상위 10개의 FAQ 목록
     */
    ResponseEntity<List<FaqResponse>> getTopFaqs();

    /**
     * 검색어 및 카테고리 필터를 기준으로 FAQ 목록을 조회합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색 조건에 맞는 FAQ 목록
     */
    ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request);

    /**
     * 특정 FAQ를 조회합니다.
     *
     * @param faqId 조회할 FAQ ID를 포함한 요청 객체
     * @return 조회된 FAQ 정보
     */
    ResponseEntity<FaqResponse> getFaqById(Long faqId);

    /**
     * 특정 키워드를 포함하는 FAQ를 검색합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색된 FAQ 목록
     */
    ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request);
}
