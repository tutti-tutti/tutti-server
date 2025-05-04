package com.tutti.server.core.member.domain;

import lombok.Getter;

@Getter
public enum InterestType {
    VALUE("가성비"),
    QUALITY("품질"),
    TREND("트렌드");

    private final String displayName;

    InterestType(String displayName) {
        this.displayName = displayName;
    }

    public static InterestType fromDisplayName(String displayName) {
        for (InterestType type : InterestType.values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}

