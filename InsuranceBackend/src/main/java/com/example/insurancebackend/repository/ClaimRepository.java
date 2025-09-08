package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim,Long> {
}