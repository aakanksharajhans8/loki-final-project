package com.insurance.documents.backend.dto;

import lombok.Data;

@Data
public class ClaimReceiptRequestDto {
    private String claimId;
    private String userId;
    private String claimantName;
    private String policyId;
    private String amountPaid;
    private String paymentDate;
}
