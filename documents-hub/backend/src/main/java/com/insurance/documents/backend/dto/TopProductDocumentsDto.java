package com.insurance.documents.backend.dto;

public class TopProductDocumentsDto {
    private String productId;
    private String productName;
    private long totalDocuments;
    private long avgSizeBytes;

    public TopProductDocumentsDto() {}
    public TopProductDocumentsDto(String productId, String productName, long totalDocuments, long avgSizeBytes) {
        this.productId = productId; this.productName = productName; this.totalDocuments = totalDocuments; this.avgSizeBytes = avgSizeBytes;
    }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public long getTotalDocuments() { return totalDocuments; }
    public void setTotalDocuments(long totalDocuments) { this.totalDocuments = totalDocuments; }
    public long getAvgSizeBytes() { return avgSizeBytes; }
    public void setAvgSizeBytes(long avgSizeBytes) { this.avgSizeBytes = avgSizeBytes; }
}

