package com.insurance.product_policy_service.service;
import com.insurance.product_policy_service.entity.Endorsement;
import com.insurance.product_policy_service.entity.Policy;
import java.util.List;

public interface PolicyService {
    Policy createPolicy(Policy policy);
    Policy getPolicyByNumber(String policyNumber);
    List<Policy> getPoliciesByCustomerId(String customerId);
    Policy updatePolicyStatus(String policyNumber, String status);
    Endorsement createEndorsement(String policyNumber, Endorsement endorsement);
    List<Endorsement> getEndorsementsForPolicy(String policyNumber);
}
