package com.insurance.documents.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "document_metadata")
@Data
@NoArgsConstructor
public class DocumentMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // The ID of the user who owns this document

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private DocumentType documentType;

    @Column(nullable = false)
    private String referenceId; // e.g., Policy Number, Claim ID

    @Column(nullable = false, unique = true)
    private String gcsFilename; // The unique filename stored in GCS

    @Column(nullable = false)
    private String originalFilename;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public DocumentMetadata(String userId, DocumentType documentType, String referenceId, String gcsFilename, String originalFilename) {
        this.userId = userId;
        this.documentType = documentType;
        this.referenceId = referenceId;
        this.gcsFilename = gcsFilename;
        this.originalFilename = originalFilename;
    }
}
