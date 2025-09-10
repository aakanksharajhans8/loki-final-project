package com.insurance.underwriting.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "underwriting_decisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnderwritingDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyId;
    private String decision; // APPROVED / REFERRED / DECLINED
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String details;

    private LocalDateTime evaluatedAt = LocalDateTime.now();
}
