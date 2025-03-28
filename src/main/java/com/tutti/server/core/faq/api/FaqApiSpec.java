package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import com.tutti.server.core.member.application.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Tag(name = "FAQ", description = "FAQ 관련 API")
public interface FaqApiSpec {

    @Operation(summary = "FAQ 카테고리 전체 목록 조회", description = "FAQ 전체 카테고리 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "카테고리 조회 성공")
    ResponseEntity<List<FaqCategoryResponse>> getCategories();

    @Operation(summary = "FAQ 메인 카테고리 목록 조회", description = "FAQ에서 사용되는 메인 카테고리 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "메인 카테고리 조회 성공")
    ResponseEntity<List<String>> getMainCategories();

    @Operation(summary = "FAQ 서브 카테고리 목록 조회", description = "FAQ에서 사용되는 서브 카테고리 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "서브 카테고리 조회 성공")
    ResponseEntity<List<String>> getSubcategories(
            @Parameter(name = "category", description = "메인 카테고리명", required = true)
            FaqMainCategory category
    );

    @Operation(summary = "FAQ 인기 질문 목록 조회", description = "조회수가 가장 높은 상위 10개의 FAQ를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "인기 FAQ 조회 성공")
    ResponseEntity<List<FaqResponse>> getTopFaqs();

    @Operation(
            summary = "FAQ 목록 조회",
            description = """
                    검색어, 메인 카테고리, 서브카테고리, 페이지 정보 등을 기반으로 FAQ 목록을 조회합니다.
                    
                    사용 가능한 카테고리 예시:
                    
                    메인 카테고리 / 서브카테고리
                    - 검색 & 커뮤니케이션
                      - 검색, 커뮤니케이션
                    - 계정 관리
                      - 계정 사용 불가, 계정 설정, 계정 찾기, 데이터 주제 권한, 등록 및 로그인, 지혜pay 계정, 챗봇 지혜 VIP
                    - 규정 및 정책
                      - 공지 사항, 약관, 플랫폼 규칙
                    - 배송
                      - 상품 미수령 보고, 상품출고 대기
                    - 애프터서비스
                      - 무료 반품, 반품/환불
                    - 주문
                      - 결제, 주문, 주문 관리
                    - 프로모션
                      - 캠페인, 코인, 쿠폰
                    - 피드백
                      - Report Seller, 주문 피드백
                    - 환불
                      - 환불 금액 부족, 환불 안 됨, 환불 절차
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "FAQ 목록 조회 성공", content = @Content(schema = @Schema(implementation = FaqListResponse.class))),
                    @ApiResponse(responseCode = "404", description = "FAQ 또는 카테고리를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<FaqListResponse> getFaqs(
            @RequestBody(
                    description = """
                            FAQ 목록 요청 파라미터
                            
                            요청 형식 예시:
                            {
                              "query": "배송",
                              "category": "주문",
                              "subcategory": "결제",
                              "page": 1,
                              "size": 10
                            }
                            """,
                    required = true,
                    content = @Content(schema = @Schema(implementation = FaqListRequest.class))
            )
            FaqListRequest request
    );


    @Operation(summary = "FAQ 단건 조회", description = "FAQ ID를 기반으로 특정 FAQ 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FAQ 조회 성공", content = @Content(schema = @Schema(implementation = FaqResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<FaqResponse> getFaqById(
            @Parameter(name = "faqId", description = "FAQ ID", required = true)
            Long faqId
    );

    @Operation(summary = "FAQ 검색", description = "특정 키워드를 포함하는 FAQ를 검색합니다.")
    @ApiResponse(responseCode = "200", description = "FAQ 검색 성공", content = @Content(schema = @Schema(implementation = FaqListResponse.class)))
    ResponseEntity<FaqListResponse> searchFaqs(
            @RequestBody(
                    description = "FAQ 검색 요청 파라미터",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FaqSearchRequest.class))
            )
            FaqSearchRequest request
    );

    @Operation(summary = "FAQ 피드백 등록", description = "FAQ에 대한 긍정 또는 부정 피드백을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드백 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    void faqFeedback(
            @Parameter(name = "faqId", description = "FAQ ID", required = true)
            Long faqId,
            @RequestBody(
                    description = "FAQ 피드백 요청 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FaqFeedbackRequest.class))
            )
            FaqFeedbackRequest request
    );

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "관리자 FAQ 등록", description = "관리자가 FAQ를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "FAQ 등록 성공", content = @Content(schema = @Schema(implementation = FaqCreateResponse.class)))
    ResponseEntity<FaqCreateResponse> createFaq(
            @RequestBody(
                    description = "FAQ 생성 요청 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FaqCreateRequest.class))
            )
            FaqCreateRequest faqCreateRequest,
            CustomUserDetails user
    );

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "관리자 FAQ 수정", description = "관리자가 FAQ를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "FAQ 수정 성공", content = @Content(schema = @Schema(implementation = FaqUpdateResponse.class)))
    ResponseEntity<FaqUpdateResponse> updateFaq(
            @Parameter(name = "faqId", description = "수정할 FAQ ID", required = true)
            Long faqId,
            @RequestBody(
                    description = "FAQ 수정 요청 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FaqUpdateRequest.class))
            )
            FaqUpdateRequest faqUpdateRequest,
            CustomUserDetails user
    );

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "관리자 FAQ 삭제", description = "관리자가 FAQ를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "FAQ 삭제 성공")
    ResponseEntity<Void> deleteFaq(
            @Parameter(name = "faqId", description = "삭제할 FAQ ID", required = true)
            Long faqId,
            CustomUserDetails user
    );
}
