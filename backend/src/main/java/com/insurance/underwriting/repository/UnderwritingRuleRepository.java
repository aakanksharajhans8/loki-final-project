package com.insurance.underwriting.repository;

import com.insurance.underwriting.model.UnderwritingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnderwritingRuleRepository extends JpaRepository<UnderwritingRule, Long> {
    List<UnderwritingRule> findByActiveTrue();
}
