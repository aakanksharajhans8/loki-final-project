package com.insurance.product_policy_service.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "endorsements")
public class Endorsement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonIgnore
    private Policy policy;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String changeDetails;

    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getChangeDetails() { return changeDetails; }
    public void setChangeDetails(String changeDetails) { this.changeDetails = changeDetails; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

