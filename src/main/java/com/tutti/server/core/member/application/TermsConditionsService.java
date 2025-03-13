package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TermsConditionsService {

    private final TermsConditionsRepository termsConditionsRepository;

    //전체 약관
    @Transactional(readOnly = true)
    public List<TermsConditions> getAllTerms() {
        return termsConditionsRepository.findAll();
    }

    //선택 약관
    @Transactional(readOnly = true)
    public TermsConditions getTermByType(TermsType termsType) {
        return termsConditionsRepository.findByTermsType(termsType)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 존재하지 않습니다: " + termsType));
    }
}
