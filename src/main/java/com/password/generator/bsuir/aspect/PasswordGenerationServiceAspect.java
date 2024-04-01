package com.password.generator.bsuir.aspect;

import com.password.generator.bsuir.dto.BulkPasswordGenerationDto;
import com.password.generator.bsuir.dto.PasswordGenerationDto;
import com.password.generator.bsuir.model.difficultyenum.Difficulty;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for logging password generation service methods.
 */
@Aspect
@Component
public class PasswordGenerationServiceAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(PasswordGenerationServiceAspect.class);

    /**
     * Logs the generation of a password.
     *
     * @param joinPoint the join point for the method
     * @return the generated password
     * @throws Throwable if an error occurs during password generation
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.generatePassword(..))")
    public Object logGeneratePassword(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Generating password...");
        Object result = joinPoint.proceed();
        logger.info("Password generated successfully.");
        return result;
    }

    /**
     * Logs the retrieval of a password by its ID.
     *
     * @param joinPoint the join point for the method
     * @return the retrieved password
     * @throws Throwable if an error occurs during password retrieval
     */
    @Around
            ("execution(* com.password.generator.bsuir.service."
                   + "PasswordGenerationService.getPasswordById(..))")
    public Object logGetPasswordById(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Retrieving password for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Password retrieved successfully.");
        return result;
    }

    /**
     * Logs the retrieval of passwords by their difficulty.
     *
     * @param joinPoint the join point for the method
     * @return the retrieved passwords
     * @throws Throwable if an error occurs during password retrieval
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.getPasswordsByDifficulty(..))")
    public Object logGetPasswordsByDifficulty(ProceedingJoinPoint joinPoint) throws Throwable {
        Difficulty difficulty = (Difficulty) joinPoint.getArgs()[0];
        logger.info("Retrieving passwords for difficulty '{}'...", difficulty);
        Object result = joinPoint.proceed();
        logger.info("Passwords by difficulty retrieved successfully.");
        return result;
    }

    /**
     * Logs the retrieval of all generated passwords.
     *
     * @param joinPoint the join point for the method
     * @return the list of all generated passwords
     * @throws Throwable if an error occurs during password retrieval
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.getAllGeneratedPasswords(..))")
    public Object logGetAllGeneratedPasswords(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Retrieving all generated passwords...");
        Object result = joinPoint.proceed();
        logger.info("All passwords retrieved successfully.");
        return result;
    }

    /**
     * Logs the deletion of a generated password by its ID.
     *
     * @param joinPoint the join point for the method
     * @return null
     * @throws Throwable if an error occurs during password deletion
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.deleteGeneratedPasswordById(..))")
    public Object logDeleteGeneratedPasswordById(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Deleting password for id '{}'...", id);
        joinPoint.proceed();
        logger.info("Password deleted successfully.");
        return null;
    }

    /**
     * Logs the retrieval of all generated passwords for a specific user.
     *
     * @param joinPoint the join point for the method
     * @return the list of all generated passwords for the user
     * @throws Throwable if an error occurs during password retrieval
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.getAllGeneratedPasswordsForUser(..))")
    public Object logGetAllGeneratedPasswordsForUser(ProceedingJoinPoint joinPoint)
            throws Throwable {
        String username = (String) joinPoint.getArgs()[0];
        logger.info("Retrieving all generated passwords for user '{}'...", username);
        Object result = joinPoint.proceed();
        logger.info("Passwords retrieved successfully.");
        return result;
    }

    /**
     * Logs the generation of bulk passwords.
     *
     * @param joinPoint the join point for the method
     * @return the list of generated bulk passwords
     * @throws Throwable if an error occurs during password generation
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.generateBulkPasswords(..))")
    public Object logGenerateBulkPasswords(ProceedingJoinPoint joinPoint) throws Throwable {
        BulkPasswordGenerationDto bulkPasswordGenerationDto =
                (BulkPasswordGenerationDto) joinPoint.getArgs()[0];
        logger.info("Generating bulk passwords with difficulty '{}', "
                        + "length '{}' and count '{}'...",
                bulkPasswordGenerationDto.getDifficulty(), bulkPasswordGenerationDto.getLength(),
                bulkPasswordGenerationDto.getCount());
        Object result = joinPoint.proceed();
        logger.info("Bulk passwords generated successfully.");
        return result;
    }

    /**
     * Logs the deletion of all generated passwords.
     *
     * @param joinPoint the join point for the method
     * @return null
     * @throws Throwable if an error occurs during password deletion
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.deleteAllGeneratedPasswords(..))")
    public Object logDeleteAllGeneratedPasswords(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Deleting all generated passwords...");
        joinPoint.proceed();
        logger.info("All generated passwords deleted successfully.");
        return null;
    }

    /**
     * Logs the generation of multiple passwords.
     *
     * @param joinPoint the join point for the method
     * @return the list of generated passwords
     * @throws Throwable if an error occurs during password generation
     */
    @Around("execution(* com.password.generator.bsuir.service."
            + "PasswordGenerationService.generatePasswords(..))")
    public Object logGeneratePasswords(ProceedingJoinPoint joinPoint) throws Throwable {
        List<PasswordGenerationDto> dtos = (List<PasswordGenerationDto>) joinPoint.getArgs()[0];
        int count = (int) joinPoint.getArgs()[1];
        logger.info("Generating passwords with difficulty '{}', length '{}' and count '{}'...",
                dtos.get(0).getDifficulty(), dtos.get(0).getLength(), count);
        Object result = joinPoint.proceed();
        logger.info("Passwords generated successfully.");
        return result;
    }
}
