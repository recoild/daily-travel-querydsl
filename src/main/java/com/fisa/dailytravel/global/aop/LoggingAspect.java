package com.fisa.dailytravel.global.aop;

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

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

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
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            HttpStatusCode status = HttpStatus.OK;
            if (result instanceof ResponseEntity<?>) {
                status = ((ResponseEntity<?>) result).getStatusCode();
            }

            logger.info(" URL: {}, HttpMethod: {}, IP: {}, HttpStatusCode: {}, ApiDuration: {}ms",
                    requestURI, httpMethod, remoteAddr, status.value(), duration);
        }
        return result;
    }
}
