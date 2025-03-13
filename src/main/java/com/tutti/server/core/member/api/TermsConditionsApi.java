package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.TermsConditionsService;
import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/members/terms")
@RequiredArgsConstructor
public class TermsConditionsApi {

    private final TermsConditionsService termsConditionsService;

    //전체 약관
    @GetMapping
    public ResponseEntity<List<TermsConditions>> getAllTerms() {
        return ResponseEntity.ok(termsConditionsService.getAllTerms());
    }

    //선택 약관
    @GetMapping("/{type}")
    public ResponseEntity<TermsConditions> getTermByType(@PathVariable TermsType type) {
        return ResponseEntity.ok(termsConditionsService.getTermByType(type));
    }
}