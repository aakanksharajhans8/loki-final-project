package com.insurance.documents.backend.dto;

import lombok.Data;

@Data
public class PolicyPdfRequestDto {
    private String policyId;
    private String userId;
    private String policyHolderName;
    private String coverageDetails;
    private String premiumAmount;
    private String startDate;
    private String endDate;
}
