package com.cams.notification.register.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "username is required")
    @Pattern(
        regexp = "^[A-Za-z][A-Za-z0-9_]{2,31}$",
        message = "username must start with a letter and be 3-32 chars (letters, digits, underscore)"
    )
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "email must not contain spaces")
    private String email;

    @NotBlank(message = "phoneNumber is required")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "phoneNumber must be 10-15 digits (optional +)"
    )
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "password must be 8+ chars with upper, lower, digit, special"
    )
    private String password;

    @NotBlank(message = "confirmPassword is required")
    private String confirmPassword;

    // getters/setters
    public String getUsername() { return username; }
    public void setUsername(String v) { this.username = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String v) { this.confirmPassword = v; }
}
