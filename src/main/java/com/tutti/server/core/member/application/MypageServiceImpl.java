package com.tutti.server.core.member.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.payload.MemberResponse;
import com.tutti.server.core.member.payload.UpdateMemberRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageServiceSpec {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponse getMyPageInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));
        return MemberResponse.fromEntity(member);
    }

    @Override
    public void updateMyPageInfo(String email, UpdateMemberRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        if (request.name() != null) {
            member.updateName(request.name());
        }
        if (request.nickname() != null) {
            member.updateNickname(request.nickname());
        }
        if (request.phone() != null) {
            member.updatePhone(request.phone());
        }
        if (request.birthDate() != null) {
            member.updateBirthDate(request.birthDate());
        }
        if (request.gender() != null) {
            member.updateGender(request.gender());
        }

        memberRepository.save(member);
    }
}
