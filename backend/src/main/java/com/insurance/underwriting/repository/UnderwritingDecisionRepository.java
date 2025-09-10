package com.insurance.underwriting.repository;

import com.insurance.underwriting.model.UnderwritingDecision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnderwritingDecisionRepository extends JpaRepository<UnderwritingDecision, Long> {
}
