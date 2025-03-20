package com.tutti.server.core.member.application;

import com.tutti.server.core.member.infrastructure.TermsConditionsRepository;
import com.tutti.server.core.member.payload.TermsConditionsResponse;
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
    public List<TermsConditionsResponse> getAllTerms() {
        return termsConditionsRepository.findAll().stream()
                .map(TermsConditionsResponse::fromEntity)
                .toList();
    }

    //선택 약관
    @Override
    @Transactional(readOnly = true)
    public TermsConditionsResponse getTermById(Long id) {
        return TermsConditionsResponse.fromEntity(termsConditionsRepository.findOne(id));
    }
}
