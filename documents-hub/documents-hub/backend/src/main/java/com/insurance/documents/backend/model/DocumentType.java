package com.insurance.documents.backend.model;

/**
 * Represents the type of document being stored.
 * This allows for a generic metadata table that can handle various business objects.
 */
public enum DocumentType {
    POLICY_DOCUMENT,  // For policy PDFs
    CLAIM_RECEIPT     // For receipts related to claims
}
