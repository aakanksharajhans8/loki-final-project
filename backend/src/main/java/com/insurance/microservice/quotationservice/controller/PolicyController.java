package com.insurance.microservice.quotationservice.controller;

import com.insurance.microservice.quotationservice.dto.AcceptanceRequestDto;
import com.insurance.microservice.quotationservice.dto.PolicyQuoteRequestDto;
import com.insurance.microservice.quotationservice.dto.PolicyQuoteResponseDto;
import com.insurance.microservice.quotationservice.entity.Policy;
import com.insurance.microservice.quotationservice.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "http://localhost:3000")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * Endpoint to get a quote. User submits their details.
     */
    @PostMapping("/quote")
    public ResponseEntity<PolicyQuoteResponseDto> getQuote(@RequestBody PolicyQuoteRequestDto requestDto) {
        PolicyQuoteResponseDto quote = policyService.calculateQuote(requestDto);
        return ResponseEntity.ok(quote);
    }

    /**
     * Endpoint for the user to accept or reject the calculated quote.
     */
    @PostMapping("/{policyId}/accept")
    public ResponseEntity<Policy> acceptQuote(@PathVariable String policyId, @RequestBody AcceptanceRequestDto acceptanceDto) {
        Policy updatedPolicy = policyService.acceptOrRejectQuote(policyId, acceptanceDto.isAccepted());
        return ResponseEntity.ok(updatedPolicy);
    }

    /**
     * Endpoint to retrieve the final policy details after acceptance.
     */
    @GetMapping("/{policyId}")
    public ResponseEntity<Policy> getPolicyDetails(@PathVariable String policyId) {
        Policy policy = policyService.getPolicyById(policyId);
        return ResponseEntity.ok(policy);
    }
}
