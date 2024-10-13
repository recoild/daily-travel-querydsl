package com.fisa.dailytravel.global.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final MeterRegistry meterRegistry;

    public LoggingAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Pointcut("execution(* com.fisa.dailytravel..controller..*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.
                requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String httpMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("ExceptionMethod: {} - ExceptionMessage: {}", joinPoint.getSignature().getName(), e.getMessage());
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            HttpStatusCode status = HttpStatus.OK;
            if (result instanceof ResponseEntity<?>) {
                status = ((ResponseEntity<?>) result).getStatusCode();
            }

            logger.info(" URL: {}, HttpMethod: {}, IP: {}, HttpStatusCode: {}, ApiDuration: {}ms",
                    requestURI, httpMethod, remoteAddr, status.value(), duration);

            // Micrometer Timer Metric
            Timer.builder("api.request.duration")
                    .tag("uri", requestURI)
                    .tag("method", httpMethod)
                    .tag("status", String.valueOf(status.value()))
                    .tag("remoteAddr", remoteAddr)
                    .publishPercentileHistogram()
                    .register(meterRegistry)
                    .record(duration, TimeUnit.MILLISECONDS);
        }
        return result;
    }
}
