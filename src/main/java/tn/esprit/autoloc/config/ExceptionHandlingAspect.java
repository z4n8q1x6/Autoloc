package tn.esprit.autoloc.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionHandlingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(
            pointcut = "execution(* tn.esprit.autoloc.service..*.*(..))",
            throwing = "failure",
            argNames = "failure")
    public void logException(JoinPoint joinPoint, Throwable failure) {
        LOGGER.error("{} threw {}: {}", joinPoint.getSignature().getName(),
                failure.getClass().getSimpleName(), failure.getMessage());
    }
}
