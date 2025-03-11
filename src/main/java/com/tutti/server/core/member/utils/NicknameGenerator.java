package com.tutti.server.core.member.utils;

import java.util.Random;

public class NicknameGenerator {

    private static final Random random = new Random();

    /**
     * 이메일을 기반으로 랜덤한 숫자를 붙여 닉네임을 생성
     */
    public static String generateNickname(String email) {
        // 이메일에서 @ 앞부분을 가져옴
        String baseName = email.split("@")[0];

        // 1000~9999 사이의 랜덤 숫자 추가
        int randomNumber = random.nextInt(9000) + 1000;
        //System.out.println("생성된 닉네임: " + baseName + randomNumber);
        return baseName + randomNumber;
    }
}
