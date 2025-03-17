package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TermsConditionsServiceImpl implements TermsConditionsServiceSpec {

    private final TermsConditionsRepository termsConditionsRepository;

    //전체 약관
    @Override
    @Transactional(readOnly = true)
    public List<TermsConditions> getAllTerms() {
        return termsConditionsRepository.findAll();
    }

    //선택 약관
    @Override
    @Transactional(readOnly = true)
    public TermsConditions getTermByType(TermsType termsType) {
        return termsConditionsRepository.findByTermsType(termsType)
                .orElseThrow(() -> new DomainException(ExceptionType.TERMS_NOT_FOUND));
    }
}
