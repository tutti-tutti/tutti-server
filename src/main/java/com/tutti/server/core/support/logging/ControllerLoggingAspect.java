package com.tutti.server.core.support.logging;

import com.tutti.server.core.member.application.CustomUserDetails;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    // 컨트롤러 클래스의 모든 메서드 실행 전
    @Before("execution(* com.tutti.server.core.payment.api..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        Long userId = extractUserId(joinPoint);

        if (userId != null) {
            log.info(">>>> [요청] 메서드: {}, 사용자ID: {}, 파라미터: {}", methodName, userId, args);
        } else {
            log.info(">>>> [요청] 메서드: {}, 사용자정보 없음, 파라미터: {}", methodName, args);
        }
    }

    // 실행 성공 후
    @AfterReturning(pointcut = "execution(* com.tutti.server.core.payment.api..*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        Long userId = extractUserId(joinPoint);

        String resultStr = String.valueOf(result);
        if (resultStr.length() > 300) {
            resultStr = resultStr.substring(0, 300) + "...";
        }

        if (userId != null) {
            log.info(">>>> [응답] 메서드: {}, 사용자ID: {}, 결과: {}", methodName, userId, resultStr);
        } else {
            log.info(">>>> [응답] 메서드: {}, 사용자정보 없음, 결과: {}", methodName, resultStr);
        }
    }

    // 예외 발생 시
    @AfterThrowing(pointcut = "execution(* com.tutti.server.core.payment.api..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();
        Long userId = extractUserId(joinPoint);

        if (userId != null) {
            log.error(">>>> [예외] 메서드: {}, 사용자ID: {}, 예외: {}", methodName, userId, ex.getMessage(),
                    ex);
        } else {
            log.error(">>>> [예외] 메서드: {}, 사용자정보 없음, 예외: {}", methodName, ex.getMessage(), ex);
        }
    }

    // 공통: 사용자 ID 추출
    private Long extractUserId(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof CustomUserDetails) {
                return ((CustomUserDetails) arg).getMemberId();
            }
        }
        return null;
    }
}
