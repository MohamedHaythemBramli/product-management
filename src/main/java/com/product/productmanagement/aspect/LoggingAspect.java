package com.product.productmanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all methods in classes annotated with @RestController
    @Pointcut("within(com.product.productmanagement.web..*)")
    public void controllerLayer() {}

    // Pointcut for all methods in classes annotated with @Service
    @Pointcut("within(com.product.productmanagement.service..*)")
    public void serviceLayer() {}

    // Log before a controller method is executed
    @Before("controllerLayer()")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("Entering Controller: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    // Log after a controller method is executed
    @AfterReturning(value = "controllerLayer()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        logger.info("Exiting Controller: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    // Log exceptions thrown by controller methods
    @AfterThrowing(value = "controllerLayer()", throwing = "exception")
    public void logExceptionInController(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in Controller: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }

    // Log before a service method is executed
    @Before("serviceLayer()")
    public void logBeforeService(JoinPoint joinPoint) {
        logger.info("Entering Service: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    // Log after a service method is executed
    @AfterReturning(value = "serviceLayer()", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        logger.info("Exiting Service: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    // Log exceptions thrown by service methods
    @AfterThrowing(value = "serviceLayer()", throwing = "exception")
    public void logExceptionInService(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in Service: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }
}

