package com.tutti.server.core.support.exception;

public enum ExceptionCode {

    E401, E500,
    A01, A02, A03, A04,//이메일 인증 관련 예외 코드
    A11, A12, A13, A14, A15, A16, A17, A18,//회원가입 관련 예외 코드
    A20, A21, A22, A23, A24, //로그인 관련 예외 코드
    A30, A31, // 비밀번호 기능 관련
    A41, A42, A43, A44, A45, A46, //JWT 관련
    A51, A52, A53, A54, //소셜 관련

    B01, B02, B03, B04, B07, B08, B09,
    C01,
    D01, D02, D03, D04, D05, D06, D07,
    F01, F02, F03, F04,
    G01, G02, G03, G04, G05, G06, G07,
    H01,
    P01, P02, P03, P04, P05, P06, P07, P08,
    R01, R02, R03, R04, R05, R06, R07, R08,
    T1, T2, T3,
    I01
}
