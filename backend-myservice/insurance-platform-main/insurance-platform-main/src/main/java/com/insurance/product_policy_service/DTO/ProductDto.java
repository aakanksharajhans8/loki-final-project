package com.insurance.product_policy_service.DTO;


import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    private Long id;
    private String productCode;
    private String name;
    private String description;
    private String productType;
    private boolean active;
    private List<ProductCoverageDto> coverages;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<ProductCoverageDto> getCoverages() { return coverages; }
    public void setCoverages(List<ProductCoverageDto> coverages) { this.coverages = coverages; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

