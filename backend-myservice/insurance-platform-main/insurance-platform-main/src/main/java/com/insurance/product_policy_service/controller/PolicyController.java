package com.insurance.product_policy_service.controller;

import com.insurance.product_policy_service.entity.Endorsement;
import com.insurance.product_policy_service.entity.Policy;
import com.insurance.product_policy_service.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) {
        Policy createdPolicy = policyService.createPolicy(policy);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    @GetMapping("/{policyNumber}")
    public ResponseEntity<Policy> getPolicyByNumber(@PathVariable String policyNumber) {
        Policy policy = policyService.getPolicyByNumber(policyNumber);
        return ResponseEntity.ok(policy);
    }

    @GetMapping("/customer/{customerId}")
    public List<Policy> getPoliciesByCustomerId(@PathVariable String customerId) {
        return policyService.getPoliciesByCustomerId(customerId);
    }

    @PatchMapping("/{policyNumber}/status")
    public ResponseEntity<Policy> updatePolicyStatus(@PathVariable String policyNumber, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        if (newStatus == null || newStatus.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Or throw a custom exception
        }
        Policy updatedPolicy = policyService.updatePolicyStatus(policyNumber, newStatus);
        return ResponseEntity.ok(updatedPolicy);
    }

    @PostMapping("/{policyNumber}/endorsements")
    public ResponseEntity<Endorsement> createEndorsement(@PathVariable String policyNumber, @Valid @RequestBody Endorsement endorsement) {
        Endorsement createdEndorsement = policyService.createEndorsement(policyNumber, endorsement);
        return new ResponseEntity<>(createdEndorsement, HttpStatus.CREATED);
    }

    @GetMapping("/{policyNumber}/endorsements")
    public List<Endorsement> getEndorsementsForPolicy(@PathVariable String policyNumber) {
        return policyService.getEndorsementsForPolicy(policyNumber);
    }
}

