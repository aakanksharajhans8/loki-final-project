package com.insurance.documents.backend.dto;


import java.time.OffsetDateTime;
import java.util.List;

public class PolicyDocumentsDto {
    private String policyId;
    private int totalDocuments;
    private List<DocumentSummary> documents;

    public static class DocumentSummary {
        private long documentId;
        private String filename;
        private String docType;
        private OffsetDateTime uploadedAt;
        private long sizeBytes;
        // getters/setters, constructors
        public DocumentSummary() {}
        public DocumentSummary(long documentId, String filename, String docType, OffsetDateTime uploadedAt, long sizeBytes) {
            this.documentId = documentId; this.filename = filename; this.docType = docType; this.uploadedAt = uploadedAt; this.sizeBytes = sizeBytes;
        }
        public long getDocumentId() { return documentId; }
        public void setDocumentId(long documentId) { this.documentId = documentId; }
        public String getFilename() { return filename; }
        public void setFilename(String filename) { this.filename = filename; }
        public String getDocType() { return docType; }
        public void setDocType(String docType) { this.docType = docType; }
        public OffsetDateTime getUploadedAt() { return uploadedAt; }
        public void setUploadedAt(OffsetDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
        public long getSizeBytes() { return sizeBytes; }
        public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }
    }

    public PolicyDocumentsDto() {}
    public PolicyDocumentsDto(String policyId, int totalDocuments, List<DocumentSummary> documents) {
        this.policyId = policyId; this.totalDocuments = totalDocuments; this.documents = documents;
    }
    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }
    public int getTotalDocuments() { return totalDocuments; }
    public void setTotalDocuments(int totalDocuments) { this.totalDocuments = totalDocuments; }
    public List<DocumentSummary> getDocuments() { return documents; }
    public void setDocuments(List<DocumentSummary> documents) { this.documents = documents; }
}

