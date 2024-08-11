package com.jupjup.www.jupjup.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
/*
•	execution(* com.jupjup.www.jupjup..*(..)):
•	*: 모든 반환 타입
•	com.jupjup.www.jupjup..*: com.jupjup.www.jupjup와 그 하위 패키지의 모든 클래스
•	*(..): 모든 메서드 이름과 모든 인자 리스트
*/
@Aspect
@Slf4j
@Component
public class LoggingAspect {


        // Before advice: 메서드 실행 전
        @Before("execution(* com.jupjup.www.jupjup..*(..))&& !within(com.jupjup.www.jupjup.jwt..* )")
        public void logBefore(JoinPoint joinPoint) {
            log.info("메서드 실행 :  {}() in class = {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        }

        // AfterReturning advice: 메서드가 정상적으로 실행 후
        @AfterReturning(pointcut = "execution(* com.jupjup.www.jupjup..*(..))&& !within(com.jupjup.www.jupjup.jwt..* )" )
        public void logAfterReturning(JoinPoint joinPoint) {
            log.info("메서드 실행 완료 : {}() in class {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        }

        // AfterThrowing advice: 메서드 실행 중 예외 발생 시
        @AfterThrowing(pointcut = "execution(* com.jupjup.www.jupjup..*(..)) && !within(com.jupjup.www.jupjup.jwt..*)", throwing = "error")
        public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
            log.error("메서드 예외 !!!!! : {}() in class {}, Error: {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName(), error.getMessage());
        }
    }


