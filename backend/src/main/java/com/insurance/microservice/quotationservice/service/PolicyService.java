package com.insurance.microservice.quotationservice.service;

import com.insurance.microservice.quotationservice.dto.PolicyQuoteRequestDto;
import com.insurance.microservice.quotationservice.dto.PolicyQuoteResponseDto;
import com.insurance.microservice.quotationservice.entity.Policy;
import com.insurance.microservice.quotationservice.exception.PolicyNotFoundException;
import com.insurance.microservice.quotationservice.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public PolicyQuoteResponseDto calculateQuote(PolicyQuoteRequestDto requestDto) {
        Policy policy = new Policy();
        policy.setId(UUID.randomUUID().toString());
        policy.setUserId(requestDto.getUserId());
        policy.setCustomerName(requestDto.getCustomerName());
        policy.setCustomerAge(requestDto.getCustomerAge());
        policy.setCustomerGender(requestDto.getCustomerGender());
        policy.setCustomerIncome(requestDto.getCustomerIncome());
        policy.setCustomerOccupation(requestDto.getCustomerOccupation());
        policy.setInsuranceType(requestDto.getInsuranceType());
        policy.setVehicleType(requestDto.getVehicleType());
        policy.setQuoteStatus("PENDING_ACCEPTANCE");
        policy.setCreatedAt(LocalDateTime.now());
        policy.setUpdatedAt(LocalDateTime.now());

        performRiskAssessment(policy);
        calculateCoverageAndPremium(policy);
        generateTermsAndConditions(policy);

        policyRepository.save(policy);

        return createResponseDto(policy);
    }

    public Policy acceptOrRejectQuote(String policyId, boolean accepted) {
        Policy policy = getPolicyById(policyId);

        if (accepted) {
            policy.setQuoteStatus("ACCEPTED");
            boolean paymentSuccessful = simulatePayment();
            policy.setPaymentStatus(paymentSuccessful ? "SUCCESS" : "FAILED");
        } else {
            policy.setQuoteStatus("REJECTED");
            policy.setPaymentStatus("NOT_APPLICABLE");
        }

        policy.setUpdatedAt(LocalDateTime.now());
        return policyRepository.save(policy);
    }

    public Policy getPolicyById(String policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + policyId));
    }

    // --- Helper Methods for Business Logic ---

    private void performRiskAssessment(Policy policy) {
        int riskScore = 50;
        if (policy.getCustomerAge() < 25 || policy.getCustomerAge() > 60) riskScore += 10;

        // Convert Long income to BigDecimal for comparison.
        if (BigDecimal.valueOf(policy.getCustomerIncome()).compareTo(new BigDecimal("500000")) < 0) {
            riskScore += 5;
        }

        if ("VEHICLE".equalsIgnoreCase(policy.getInsuranceType())) riskScore += 15;
        policy.setRiskScore(Math.min(riskScore, 100));
    }

    private void calculateCoverageAndPremium(Policy policy) {
        BigDecimal baseCoverage;
        String insuranceType = policy.getInsuranceType().toUpperCase();

        switch (insuranceType) {
            case "LIFE":
                baseCoverage = new BigDecimal("2500000"); // 25 Lakhs base
                break;
            case "HEALTH":
                baseCoverage = new BigDecimal("750000");  // 7.5 Lakhs base
                break;
            case "VEHICLE":
                baseCoverage = new BigDecimal("500000");  // 5 Lakhs base
                break;
            default:
                baseCoverage = new BigDecimal("100000");
        }

        // Convert Long income to BigDecimal for calculation.
        BigDecimal incomeAsBigDecimal = BigDecimal.valueOf(policy.getCustomerIncome());
        BigDecimal calculatedCoverage = baseCoverage.add(incomeAsBigDecimal.multiply(BigDecimal.valueOf(0.5)))
                .multiply(BigDecimal.valueOf(1 - (policy.getRiskScore() / 200.0)));

        if ("HEALTH".equalsIgnoreCase(policy.getInsuranceType())) {
            if (calculatedCoverage.compareTo(new BigDecimal("1000000")) > 0) {
                calculatedCoverage = new BigDecimal("1000000");
            }
        }

        long roundedCoverage = (long) (Math.round(calculatedCoverage.doubleValue() / 100000.0) * 100000.0);

        BigDecimal monthlyPremiumDecimal = BigDecimal.valueOf(roundedCoverage)
                .multiply(new BigDecimal("0.001"))
                .multiply(BigDecimal.valueOf(1 + (policy.getRiskScore() / 100.0)));

        policy.setCoverageAmount(roundedCoverage);
        policy.setMonthlyPremium((long) Math.ceil(monthlyPremiumDecimal.doubleValue()));
        policy.setPremiumTermMonths(120);
    }

    private void generateTermsAndConditions(Policy policy) {
        policy.setTermsAndConditions("This is a standard insurance policy. The coverage of " + policy.getCoverageAmount()
                + " is valid for " + policy.getPremiumTermMonths() + " months, with a monthly premium of "
                + policy.getMonthlyPremium() + ". All claims are subject to verification.");
    }

    private boolean simulatePayment() {
        return new Random().nextBoolean();
    }

    private PolicyQuoteResponseDto createResponseDto(Policy policy) {
        PolicyQuoteResponseDto response = new PolicyQuoteResponseDto();
        response.setPolicyId(policy.getId());
        response.setInsuranceType(policy.getInsuranceType());
        response.setCoverageAmount(policy.getCoverageAmount());
        response.setMonthlyPremium(policy.getMonthlyPremium());
        response.setPremiumTermMonths(policy.getPremiumTermMonths());
        response.setTermsAndConditions(policy.getTermsAndConditions());
        return response;
    }
}

