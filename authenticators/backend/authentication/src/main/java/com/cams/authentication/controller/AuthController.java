package com.cams.authentication.controller;

import com.cams.authentication.dto.ChangePasswordRequest;
import com.cams.authentication.dto.LoginRequest;
import com.cams.authentication.dto.PasswordResetRequest;
import com.cams.authentication.dto.SignupRequest;
import com.cams.authentication.entity.User;
import com.cams.authentication.exception.EmailAlreadyExistsException;
import com.cams.authentication.exception.InvalidCredentialsException;
import com.cams.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            User user = authService.signup(signupRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.login(loginRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String email) {
        authService.logout(email);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequest request) {
        try {
            authService.createPasswordResetToken(request.getEmail());
            return new ResponseEntity<>("Password reset link sent to your email", HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePasswordRequest request) {
        try {
            authService.changePassword(request);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}