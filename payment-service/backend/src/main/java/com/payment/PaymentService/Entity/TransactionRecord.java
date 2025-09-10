package com.payment.PaymentService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Policy type is required")
    private String policyType;

    @NotBlank(message = "Policy number is required")
    private String policyNumber;

    @CreationTimestamp
    private Instant createdAt;

    private Long amountPaise;

    private Long taxPaise;

    private String currency = "INR";

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

    private String status;
}
