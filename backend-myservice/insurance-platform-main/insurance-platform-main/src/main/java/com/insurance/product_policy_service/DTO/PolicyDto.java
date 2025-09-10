package com.insurance.product_policy_service.DTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PolicyDto {
    private Long id;
    private String policyNumber;
    private Long productId;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal premiumAmount;
    private List<EndorsementDto> endorsements;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; }
    public List<EndorsementDto> getEndorsements() { return endorsements; }
    public void setEndorsements(List<EndorsementDto> endorsements) { this.endorsements = endorsements; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

