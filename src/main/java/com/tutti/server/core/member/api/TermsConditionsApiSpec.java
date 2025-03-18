package com.tutti.server.core.member.api;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "약관 API", description = "이용 약관 및 정책 관련 API")
@RequestMapping("members/terms")
public interface TermsConditionsApiSpec {

    @Operation(summary = "전체 약관 조회", description = "모든 약관을 조회합니다.")
    @GetMapping
    ResponseEntity<List<TermsConditions>> getAllTerms();

    @Operation(summary = "선택 약관 조회", description = "특정 약관 타입을 기준으로 약관을 조회합니다.")
    @GetMapping("/{type}")
    ResponseEntity<TermsConditions> getTermByType(@PathVariable TermsType type);
}
