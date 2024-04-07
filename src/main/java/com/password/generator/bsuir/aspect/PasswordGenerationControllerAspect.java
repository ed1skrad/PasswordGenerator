package com.password.generator.bsuir.aspect;

import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.exception.PasswordGenerationException;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * This class represents an aspect for logging
 * and validating methods in the PasswordGenerationController.
 * It uses Spring AOP to intercept method calls
 * and perform additional operations before they are executed.
 */
@Aspect
@Component
public class PasswordGenerationControllerAspect {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PasswordGenerationControllerAspect.class);

    /**
     * This pointcut matches all methods in the PasswordGenerationController.
     */
    @Pointcut("execution(* com.password.generator."
            + "bsuir.controller.PasswordGenerationController.*(..))")
    public void passwordGenerationControllerMethods() {}

    /**
     * This method logs the call to a method
     * matched by the passwordGenerationControllerMethods pointcut.
     *
     * @param joinPoint the join point representing the method call
     */
    @Before("passwordGenerationControllerMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Calling method: {} with arguments: {}",
                methodName, Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * This method validates the PasswordGenerationDto object
     * passed as an argument to a method matched
     * by the passwordGenerationControllerMethods pointcut.
     *
     * @param dto the PasswordGenerationDto object to validate
     */
    @Before("passwordGenerationControllerMethods() && args(dto)")
    public void validatePasswordGenerationDto(PasswordGenerationDto dto) {
        if (dto.getLength() > 255) {
            LOGGER.error("Password length should not more than 255 characters");
            throw new PasswordGenerationException(
                    "Password length should not more than 255 characters");
        }
    }

    /**
     * This method validates the Difficulty enum passed as
     * an argument to a method matched by the passwordGenerationControllerMethods pointcut.
     *
     * @param difficulty the Difficulty enum to validate
     */
    @Before("passwordGenerationControllerMethods() && args(difficulty)")
    public void validateDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            LOGGER.error("Difficulty should not be null");
            throw new PasswordGenerationException("Difficulty should not be null");
        }
    }

    /**
     * This method validates the username string passed as
     * an argument to a method matched by the passwordGenerationControllerMethods pointcut.
     *
     * @param username the username string to validate
     */
    @Before("passwordGenerationControllerMethods() && args(username)")
    public void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            LOGGER.error("Username should not be null or empty");
            throw new PasswordGenerationException("Username should not be null or empty");
        }
    }
}
