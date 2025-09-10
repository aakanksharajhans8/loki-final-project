package com.insurance.product_policy_service.entity;

import jakarta.persistence.*;
        import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productCode;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String productType;

    private boolean active = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductCoverage> coverages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
    public List<ProductCoverage> getCoverages() { return coverages; }
    public void setCoverages(List<ProductCoverage> coverages) { this.coverages = coverages; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}