package com.insurance.documents.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "fact_documents", schema = "gold_insurance",
        indexes = {
                @Index(name = "idx_fact_documents_date", columnList = "date_key"),
                @Index(name = "idx_fact_documents_policy", columnList = "policy_id")
        })
public class FactDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "policy_id", nullable = false)
    private String policyId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "date_key", nullable = false)
    private LocalDate dateKey;

    @Column(name = "filename")
    private String filename;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "is_pdf")
    private Boolean isPdf;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "uploaded_at")
    private OffsetDateTime uploadedAt;

    @Column(name = "src_system")
    private String srcSystem;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public LocalDate getDateKey() { return dateKey; }
    public void setDateKey(LocalDate dateKey) { this.dateKey = dateKey; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }

    public Boolean getIsPdf() { return isPdf; }
    public void setIsPdf(Boolean isPdf) { this.isPdf = isPdf; }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

    public OffsetDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(OffsetDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public String getSrcSystem() { return srcSystem; }
    public void setSrcSystem(String srcSystem) { this.srcSystem = srcSystem; }
}
