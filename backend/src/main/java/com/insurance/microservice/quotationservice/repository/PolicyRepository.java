package com.insurance.microservice.quotationservice.repository;

import com.insurance.microservice.quotationservice.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
}


