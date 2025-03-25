// MemberResponse.java
package com.tutti.server.core.member.payload;

import com.tutti.server.core.member.domain.Member;
import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String nickname,
        String phone,
        LocalDate birthDate,
        String gender
) {

    public static MemberResponse fromEntity(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhone(),
                member.getBirthDate(),
                member.getGender()
        );
    }
}

