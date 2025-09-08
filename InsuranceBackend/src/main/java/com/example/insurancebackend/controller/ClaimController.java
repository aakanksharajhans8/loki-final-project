package com.example.insurancebackend.controller;

import com.example.insurancebackend.entity.Claim;
import com.example.insurancebackend.service.ClaimService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/claim")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping("/register")
    public Claim registerClaim(@RequestBody Claim claimRequest) {
        return claimService.registerClaim(claimRequest);
    }
}