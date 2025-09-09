package com.insurance.microservice.quotationservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PolicyQuoteRequestDto {
    // Reverted from Long back to String for UUID compatibility.
    private String userId;

    private String customerName;
    private int customerAge;
    private String customerGender;
    private Long customerIncome;
    private String customerOccupation;
    private String insuranceType;
    private String vehicleType;
}

