package com.password.generator.bsuir.security.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object for a sign-up request.
 */
public class SignUpRequest {

    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 255;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 255;

    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH,
            message = "Имя пользователя должно содержать от "
                    + USERNAME_MIN_LENGTH + " до " + USERNAME_MAX_LENGTH + " символов")
    @NotNull(message = "Имя пользователя не может быть null")
    private String username;

    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH,
            message = "Адрес электронной почты должен содержать от "
                    + EMAIL_MIN_LENGTH + " до " + EMAIL_MAX_LENGTH + " символов")
    @NotNull(message = "Адрес электронной почты не может быть null")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

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
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
