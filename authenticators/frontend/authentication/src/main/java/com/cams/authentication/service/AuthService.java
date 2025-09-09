package com.cams.authentication.service;

import com.cams.authentication.dto.ChangePasswordRequest;
import com.cams.authentication.dto.LoginRequest;
import com.cams.authentication.dto.SignupRequest;
import com.cams.authentication.entity.User;
import com.cams.authentication.entity.Role;
import com.cams.authentication.exception.EmailAlreadyExistsException;
import com.cams.authentication.exception.InvalidCredentialsException;
import com.cams.authentication.repository.RoleRepository;
import com.cams.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initializeRoles() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }

    @Transactional
    public User signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already in use.");
        }

        initializeRoles();

        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Assign a default role
        Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
        if (userRoleOptional.isPresent()) {
            user.getRoles().add(userRoleOptional.get());
        } else {
            // Handle case where role is not found, although initializeRoles should prevent this
            throw new RuntimeException("Default role not found.");
        }

        return userRepository.save(user);
    }

    @Transactional
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        user.setLoginTime(Instant.now());
        user.setLastActivity(Instant.now());
        return userRepository.save(user);
    }

    @Transactional
    public User logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found."));

        user.setLogoutTime(Instant.now());
        return userRepository.save(user);
    }

    @Transactional
    public void createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found."));
        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);
        // In a real application, you would send this token via email
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByResetToken(request.getResetToken())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid or expired token."));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        userRepository.save(user);
    }

    @Transactional
    public void updateLastActivity(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastActivity(Instant.now());
            userRepository.save(user);
        });
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}