package com.insurance.product_policy_service.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EndorsementDto {
    private Long id;
    private LocalDate effectiveDate;
    private String description;
    private String changeDetails;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getChangeDetails() { return changeDetails; }
    public void setChangeDetails(String changeDetails) { this.changeDetails = changeDetails; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

