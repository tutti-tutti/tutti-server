package com.tutti.server.core.faq.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FaqMainCategory {

    계정_관리("계정 관리"),
    검색_커뮤니케이션("검색 & 커뮤니케이션"),
    프로모션("프로모션"),
    주문("주문"),
    배송("배송"),
    애프터서비스("애프터서비스"),
    환불("환불"),
    피드백("피드백"),
    규정_및_정책("규정 및 정책");

    private final String displayName;

    FaqMainCategory(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static FaqMainCategory fromDisplayName(String displayName) {
        for (FaqMainCategory category : FaqMainCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("해당 이름의 카테고리가 존재하지 않습니다: " + displayName);
    }

}
