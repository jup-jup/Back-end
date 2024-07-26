package com.jupjup.www.jupjup.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Before advice: 실행 전
    @Before("execution(* OAuthJWT..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("메서드 실행 :  {}() in class = {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
    }

    // AfterReturning advice: 메서드가 정상적으로 실행 후
    @AfterReturning(pointcut = "execution(* OAuthJWT..*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.info("메서드 실행 완료 : {}() in class {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
    }

    // AfterThrowing advice: 메서드 실행 중 예외 발생 시
    @AfterThrowing(pointcut = "execution(* OAuthJWT..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("메서드 예외 !!!!! : {}() in class {}, Error: {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName(), error.getMessage());
    }


}
