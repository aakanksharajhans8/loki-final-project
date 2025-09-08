package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "settleddb")
public class SettledClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer policyId;
    private String name;
    private String phoneNo;
    private String emailId;
    private String claimType;
    private Double claimAmount;
    private Double settledAmount;
    private String status;
}
