package com.insurance.product_policy_service.service;

import com.insurance.product_policy_service.entity.Endorsement;
import com.insurance.product_policy_service.entity.Policy;
import com.insurance.product_policy_service.exception.ResourceNotFoundException;
import com.insurance.product_policy_service.repository.EndorsementRepository;
import com.insurance.product_policy_service.repository.PolicyRepository;
import com.insurance.product_policy_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final EndorsementRepository endorsementRepository;
    private final ProductRepository productRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository, EndorsementRepository endorsementRepository, ProductRepository productRepository) {
        this.policyRepository = policyRepository;
        this.endorsementRepository = endorsementRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Policy createPolicy(Policy policy) {
        // Ensure the product exists before creating a policy for it
        productRepository.findById(policy.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + policy.getProductId()));

        // Generate a unique policy number
        policy.setPolicyNumber("POL-" + UUID.randomUUID().toString().toUpperCase());
        policy.setCreatedAt(LocalDateTime.now());
        policy.setUpdatedAt(LocalDateTime.now());
        return policyRepository.save(policy);
    }

    @Override
    public Policy getPolicyByNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + policyNumber));
    }

    @Override
    public List<Policy> getPoliciesByCustomerId(String customerId) {
        return policyRepository.findByCustomerId(customerId);
    }

    @Override
    public Policy updatePolicyStatus(String policyNumber, String status) {
        Policy policy = getPolicyByNumber(policyNumber);
        policy.setStatus(status);
        policy.setUpdatedAt(LocalDateTime.now());
        return policyRepository.save(policy);
    }

    @Override
    public Endorsement createEndorsement(String policyNumber, Endorsement endorsement) {
        Policy policy = getPolicyByNumber(policyNumber);
        endorsement.setPolicy(policy);
        endorsement.setCreatedAt(LocalDateTime.now());
        return endorsementRepository.save(endorsement);
    }

    @Override
    public List<Endorsement> getEndorsementsForPolicy(String policyNumber) {
        Policy policy = getPolicyByNumber(policyNumber);
        return policy.getEndorsements();
    }
}

