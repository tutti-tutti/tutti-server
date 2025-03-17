package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.TermsConditionsServiceImpl;
import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TermsConditionsApi implements TermsConditionsApiSpec {

    private final TermsConditionsServiceImpl termsConditionsServiceImpl;

    //전체 약관
    @Override
    public ResponseEntity<List<TermsConditions>> getAllTerms() {
        return ResponseEntity.ok(termsConditionsServiceImpl.getAllTerms());
    }

    //선택 약관
    @Override
    public ResponseEntity<TermsConditions> getTermByType(@PathVariable TermsType type) {
        return ResponseEntity.ok(termsConditionsServiceImpl.getTermByType(type));
    }
}
