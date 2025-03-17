package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;
import java.util.List;

public interface TermsConditionsServiceSpec {

    List<TermsConditions> getAllTerms();

    TermsConditions getTermByType(TermsType termsType);
}

