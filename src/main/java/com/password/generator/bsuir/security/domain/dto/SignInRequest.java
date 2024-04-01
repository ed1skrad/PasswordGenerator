package com.password.generator.bsuir.security.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object for a sign-in request.
 */
public class SignInRequest {

    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 255;

    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH,
            message = "Имя пользователя должно содержать от " + USERNAME_MIN_LENGTH
            + " до " + USERNAME_MAX_LENGTH + " символов")
    @NotNull(message = "Имя пользователя не может быть null")
    private String username;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH,
            message = "Длина пароля должна быть от " + PASSWORD_MIN_LENGTH
            + " до " + PASSWORD_MAX_LENGTH + " символов")
    @NotNull(message = "Пароль не может быть null")
    private String password;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
