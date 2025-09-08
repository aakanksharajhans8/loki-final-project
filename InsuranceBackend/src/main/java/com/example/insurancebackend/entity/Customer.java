package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "policy")
public class Customer {

    @Id
    @Column(name = "policy_id")
    private Integer policyId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "policy_type")
    private String policyType;

    @Column(name = "invested_amount")
    private Double investedAmount;

    @Column(name = "valid_till")
    private String validTill;
}