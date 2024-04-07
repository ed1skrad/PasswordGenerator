package com.password.generator.bsuir.aspect;

import com.password.generator.bsuir.security.controller.AuthController;
import com.password.generator.bsuir.security.domain.dto.SignInRequest;
import com.password.generator.bsuir.security.domain.dto.SignUpRequest;
import java.util.Arrays;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * This aspect logs all method calls in the {@link AuthController} class.
 */

@Aspect
@Component
public class AuthControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerAspect.class);

    /**
     * Logs a message before each method call in the {@link AuthController} class.
     *
     * @param joinPoint the join point representing the method call
     */
    @Before("execution(* com.password.generator.bsuir.security.controller.AuthController.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        LOGGER.info("Calling method: {}", methodName);
        switch (methodName) {
            case "signUp" -> {
                SignUpRequest signUpRequest = (SignUpRequest) args[0];
                LOGGER.info("Signing up user: {}", signUpRequest.getUsername());
            }
            case "signIn" -> {
                SignInRequest signInRequest = (SignInRequest) args[0];
                LOGGER.info("Signing in user: {}", signInRequest.getUsername());
            }
            case "logout" -> {
                HttpServletRequest request = (HttpServletRequest) args[0];
                String username = (String) request.getAttribute("username");
                LOGGER.info("Logging out user: {}", username);
            }
            default -> {
                LOGGER.info("Error!");
            }
        }
    }
}
