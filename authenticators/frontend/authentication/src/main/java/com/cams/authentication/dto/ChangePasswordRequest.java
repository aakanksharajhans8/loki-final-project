package com.cams.authentication.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String resetToken;
    private String newPassword;
}
