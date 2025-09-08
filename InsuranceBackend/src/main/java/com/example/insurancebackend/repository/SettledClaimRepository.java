package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.SettledClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettledClaimRepository extends JpaRepository<SettledClaim, Long> {
}