package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.TermsConditionsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "약관 API", description = "이용 약관 및 정책 관련 API")
public interface TermsConditionsApiSpec {

    @Operation(summary = "전체 약관 조회", description = "모든 약관을 조회합니다.")
    ResponseEntity<List<TermsConditionsResponse>> getAllTerms();

    @Operation(summary = "선택 약관 조회", description = "특정 약관 타입을 기준으로 약관을 조회합니다.")
    ResponseEntity<TermsConditionsResponse> getTermById(Long id);
}
