package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.TermsConditionsServiceImpl;
import com.tutti.server.core.member.payload.TermsConditionsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members/terms")
public class TermsConditionsApi implements TermsConditionsApiSpec {

    private final TermsConditionsServiceImpl termsConditionsServiceImpl;

    //전체 약관
    @Override
    @GetMapping
    public ResponseEntity<List<TermsConditionsResponse>> getAllTerms() {
        return ResponseEntity.ok(termsConditionsServiceImpl.getAllTerms());
    }

    //선택 약관
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TermsConditionsResponse> getTermById(@PathVariable Long id) {
        return ResponseEntity.ok(termsConditionsServiceImpl.getTermById(id));
    }
}
