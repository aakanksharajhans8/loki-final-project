package com.insurance.documents.backend.dto;

import java.time.LocalDate;

public class DailyDocumentStatsDto {
    private LocalDate date;
    private long totalUploads;
    private long pdfGenerated;
    private long avgSizeBytes;

    // constructors, getters, setters
    public DailyDocumentStatsDto() {}
    public DailyDocumentStatsDto(LocalDate date, long totalUploads, long pdfGenerated, long avgSizeBytes) {
        this.date = date;
        this.totalUploads = totalUploads;
        this.pdfGenerated = pdfGenerated;
        this.avgSizeBytes = avgSizeBytes;
    }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public long getTotalUploads() { return totalUploads; }
    public void setTotalUploads(long totalUploads) { this.totalUploads = totalUploads; }
    public long getPdfGenerated() { return pdfGenerated; }
    public void setPdfGenerated(long pdfGenerated) { this.pdfGenerated = pdfGenerated; }
    public long getAvgSizeBytes() { return avgSizeBytes; }
    public void setAvgSizeBytes(long avgSizeBytes) { this.avgSizeBytes = avgSizeBytes; }
}
