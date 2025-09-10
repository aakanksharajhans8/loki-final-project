package com.insurance.product_policy_service.DTO;

import java.math.BigDecimal;

public class ProductCoverageDto {
    private Long id;
    private String coverageCode;
    private String name;
    private String description;
    private BigDecimal coverageLimit;
    private BigDecimal deductible;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCoverageCode() { return coverageCode; }
    public void setCoverageCode(String coverageCode) { this.coverageCode = coverageCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getCoverageLimit() { return coverageLimit; }
    public void setCoverageLimit(BigDecimal coverageLimit) { this.coverageLimit = coverageLimit; }
    public BigDecimal getDeductible() { return deductible; }
    public void setDeductible(BigDecimal deductible) { this.deductible = deductible; }
}

