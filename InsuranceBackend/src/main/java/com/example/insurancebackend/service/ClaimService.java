package com.example.insurancebackend.service;

import com.example.insurancebackend.entity.*;
import com.example.insurancebackend.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClaimService {

    private final CustomerRepository customerRepository;
    private final ClaimRepository claimRepository;
    private final SettledClaimRepository settledClaimRepository;

    public ClaimService(CustomerRepository customerRepository,
                        ClaimRepository claimRepository,
                        SettledClaimRepository settledClaimRepository) {
        this.customerRepository = customerRepository;
        this.claimRepository = claimRepository;
        this.settledClaimRepository = settledClaimRepository;
    }

    public Claim registerClaim(Claim claimRequest) {
        try {
            Customer customer = customerRepository.findById(claimRequest.getPolicyId())
                    .orElseThrow(() -> new RuntimeException("Invalid Policy ID"));

            if (LocalDate.parse(customer.getValidTill()).isBefore(LocalDate.now())) {
                throw new RuntimeException("Policy Expired");
            }

            double sumInsured = customer.getInvestedAmount() * 5;
            double settledAmount;

            switch (customer.getPolicyType().toLowerCase()) {
                case "health":
                    double coPayPercent = 0.2;
                    double payable = claimRequest.getClaimAmount() * (1 - coPayPercent);
                    settledAmount = Math.min(payable, sumInsured);
                    break;

                case "motor":
                    double deductible = 5000;
                    double afterDeduction = Math.max(claimRequest.getClaimAmount() - deductible, 0);
                    settledAmount = Math.min(afterDeduction, sumInsured);
                    break;

                case "life":
                    settledAmount = Math.min(claimRequest.getClaimAmount(), sumInsured);
                    break;

                default:
                    throw new RuntimeException("Unknown policy type: " + customer.getPolicyType());
            }

            claimRequest.setStatus("SETTLED");
            claimRequest.setSettledAmount(settledAmount);

            Claim savedClaim = claimRepository.save(claimRequest);

            SettledClaim settled = new SettledClaim(
                    null,
                    customer.getPolicyId(),
                    customer.getCustomerName(),
                    customer.getMobileNumber(),
                    customer.getEmail(),
                    claimRequest.getClaimType(),
                    claimRequest.getClaimAmount(),
                    settledAmount,
                    "SETTLED"
            );
            settledClaimRepository.save(settled);

            return savedClaim;

        } catch (Exception ex) {
            SettledClaim unsettled = new SettledClaim(
                    null,
                    claimRequest.getPolicyId(),
                    claimRequest.getName(),
                    claimRequest.getPhoneNo(),
                    claimRequest.getEmailId(),
                    claimRequest.getClaimType(),
                    claimRequest.getClaimAmount(),
                    0.0,
                    "UNSETTLED"
            );
            settledClaimRepository.save(unsettled);

            throw new RuntimeException("Claim could not be settled: " + ex.getMessage());
        }
    }
}