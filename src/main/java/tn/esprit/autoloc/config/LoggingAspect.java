package tn.esprit.autoloc.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    @Before("execution(* tn.esprit.autoloc.service..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("logBefore: " + joinPoint.getSignature().getName());
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* tn.esprit.autoloc.service..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("logAfter: " + joinPoint.getSignature().getName());
    }
}
