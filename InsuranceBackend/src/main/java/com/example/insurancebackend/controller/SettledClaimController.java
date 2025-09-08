package com.example.insurancebackend.controller;

import com.example.insurancebackend.entity.SettledClaim;
import com.example.insurancebackend.repository.SettledClaimRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settled")
public class SettledClaimController {

    private final SettledClaimRepository settledClaimRepository;

    public SettledClaimController(SettledClaimRepository settledClaimRepository) {
        this.settledClaimRepository = settledClaimRepository;
    }

    @GetMapping
    public List<SettledClaim> getAllSettledClaims() {
        return settledClaimRepository.findAll();
    }
}