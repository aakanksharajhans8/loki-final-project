package com.insurance.documents.backend.repository;



import com.insurance.documents.backend.model.DimPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimPolicyRepository extends JpaRepository<DimPolicy, String> {
    DimPolicy findByPolicyId(String policyId);
}

