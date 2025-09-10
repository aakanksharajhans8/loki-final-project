package com.insurance.underwriting.dto;

import lombok.Data;
import java.util.Map;

@Data
public class EvaluateRequestDto {
    private String policyId;
    private Map<String, Object> riskFactors; // e.g., {"age":55,"smoker":true,"sumInsured":1500000}
}
