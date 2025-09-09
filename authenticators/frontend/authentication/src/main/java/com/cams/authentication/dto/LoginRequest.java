package com.cams.authentication.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user login requests.
 */
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}