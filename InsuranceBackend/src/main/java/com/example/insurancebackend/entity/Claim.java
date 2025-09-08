package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private Integer policyId;
    private String name;
    private String phoneNo;
    private String emailId;
    private String claimType;
    private String documents;
    private String status;

    private Double claimAmount;
    private Double settledAmount;
}