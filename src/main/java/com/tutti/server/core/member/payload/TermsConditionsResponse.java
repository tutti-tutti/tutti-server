package com.tutti.server.core.member.payload;

import com.tutti.server.core.member.domain.TermsConditions;
import com.tutti.server.core.member.domain.TermsType;

public record TermsConditionsResponse(
        Long id,
        TermsType termsType,
        String displayName,
        String content,
        boolean required
) {

    public static TermsConditionsResponse fromEntity(TermsConditions terms) {
        return new TermsConditionsResponse(
                terms.getId(),
                terms.getTermsType(),
                terms.getDisplayName(),
                terms.getContent(),
                terms.isRequired()
        );
    }
}
