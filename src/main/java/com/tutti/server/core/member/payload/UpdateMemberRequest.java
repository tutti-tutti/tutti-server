package com.tutti.server.core.member.payload;

import java.time.LocalDate;

public record UpdateMemberRequest(
        String name,
        String nickname,
        String phone,
        LocalDate birthDate,
        String gender
) {

}
