package com.insurance.microservice.quotationservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PolicyQuoteResponseDto {

    private String policyId;
    private String insuranceType;
    private Long coverageAmount; // Changed to Long
    private Long monthlyPremium; // Changed to Long
    private Integer premiumTermMonths;
    private String termsAndConditions;
}

