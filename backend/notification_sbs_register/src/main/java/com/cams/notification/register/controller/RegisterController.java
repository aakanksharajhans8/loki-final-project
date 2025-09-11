package com.cams.notification.register.controller;
import com.cams.notification.register.dto.*; import com.cams.notification.register.model.*; import com.cams.notification.register.repo.*; import com.cams.notification.register.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class RegisterController {
  private final RegistrationRepository repository; private final MailService mail;
  private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
  public RegisterController(RegistrationRepository repository, MailService mail){ this.repository=repository; this.mail=mail; }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest req){
    if(!req.getPassword().equals(req.getConfirmPassword())) throw new IllegalArgumentException("password and confirmPassword do not match");
    String hash=encoder.encode(req.getPassword());
    RegisteredUser user=new RegisteredUser(req.getUsername().trim(), req.getEmail().trim(), req.getPhoneNumber().trim(), hash);
    repository.save(user);
    String subject="Registration Successful";
    String body=String.format("Hi %s,%n%nYour registration is complete.%n%nThanks,%nCAMS Team", req.getUsername());
    mail.sendPlainText(req.getEmail(), subject, body);
    return ResponseEntity.ok(new ApiResponse(true, "User registered & email sent to " + req.getEmail()));
  }

  @GetMapping("/health")
  public ResponseEntity<ApiResponse> health(){ return ResponseEntity.ok(new ApiResponse(true,"OK")); }
}
