package com.insurance.microservice.quotationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies")
@Data
public class Policy {

    @Id
    private String id;

    // Customer details
    private String userId;
    private String customerName;
    private int customerAge;
    private String customerGender;
    private Long customerIncome;
    private String customerOccupation;

    // Insurance details
    private String insuranceType;
    private String vehicleType;

    // Calculated fields
    private int riskScore;

    // UPDATED: Changed from BigDecimal to Long
    private Long coverageAmount;
    private Long monthlyPremium;

    private int premiumTermMonths;
    @Column(columnDefinition = "TEXT")
    private String termsAndConditions;

    // Status fields
    private String quoteStatus;
    private String paymentStatus;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
