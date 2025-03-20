package com.tutti.server.core.member.application;

import com.tutti.server.core.member.payload.TermsConditionsResponse;
import java.util.List;

public interface TermsConditionsServiceSpec {

    List<TermsConditionsResponse> getAllTerms();

    TermsConditionsResponse getTermById(Long id);
}

