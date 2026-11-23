package tn.esprit.autoloc.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class PerformanceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceAspect.class);
    private static final long SLOW_CALL_THRESHOLD_NANOS = TimeUnit.SECONDS.toNanos(1);

    @Around("execution(* tn.esprit.autoloc.service..*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedNanos = System.nanoTime() - start;
            double elapsedMillis = elapsedNanos / 1_000_000.0;
            String methodName = joinPoint.getSignature().getName();

            if (elapsedNanos > SLOW_CALL_THRESHOLD_NANOS) {
                LOGGER.warn("{} took {} ms (over 1000 ms)", methodName, elapsedMillis);
            } else {
                LOGGER.info("{} took {} ms", methodName, elapsedMillis);
            }
        }
    }
}
