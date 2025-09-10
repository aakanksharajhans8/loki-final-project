package com.insurance.documents.backend.service;

import com.insurance.documents.backend.dto.DailyDocumentStatsDto;
import com.insurance.documents.backend.dto.PolicyDocumentsDto;
import com.insurance.documents.backend.dto.TopProductDocumentsDto;
import com.insurance.documents.backend.model.DimPolicy;
import com.insurance.documents.backend.model.FactDocument;
import com.insurance.documents.backend.repository.DimPolicyRepository;
import com.insurance.documents.backend.repository.FactDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    private final FactDocumentRepository factRepo;
    private final DimPolicyRepository policyRepo;

    public AnalyticsService(FactDocumentRepository factRepo, DimPolicyRepository policyRepo) {
        this.factRepo = factRepo;
        this.policyRepo = policyRepo;
    }

    /**
     * Daily stats: uploads, PDFs, average size.
     */
    public List<DailyDocumentStatsDto> getDailyDocumentStats(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching daily stats between {} and {}", startDate, endDate);
        List<Object[]> rows = factRepo.findDailyStats(startDate, endDate);

        List<DailyDocumentStatsDto> result = new ArrayList<>();
        for (Object[] row : rows) {
            result.add(new DailyDocumentStatsDto(
                    (LocalDate) row[0],
                    ((Number) row[1]).longValue(),
                    ((Number) row[2]).longValue(),
                    row[3] != null ? ((Number) row[3]).longValue() : 0L
            ));
        }
        return result;
    }

    /**
     * Documents linked to a specific policy.
     */
    public PolicyDocumentsDto getDocumentsByPolicy(String policyId) {
        log.info("Fetching documents for policy {}", policyId);

        List<FactDocument> docs = factRepo.findByPolicyIdOrderByUploadedAtDesc(policyId);

        List<PolicyDocumentsDto.DocumentSummary> summaries = new ArrayList<>();
        for (FactDocument f : docs) {
            summaries.add(new PolicyDocumentsDto.DocumentSummary(
                    f.getDocumentId(),
                    f.getFilename(),
                    f.getDocumentType(),
                    f.getUploadedAt(),
                    f.getSizeBytes()
            ));
        }

        return new PolicyDocumentsDto(policyId, docs.size(), summaries);
    }

    /**
     * Top N products by document volume.
     */
    public List<TopProductDocumentsDto> getTopProducts(LocalDate startDate, LocalDate endDate, int topN) {
        log.info("Fetching top {} products from {} to {}", topN, startDate, endDate);
        List<Object[]> rows = factRepo.findTopProducts(startDate, endDate);

        List<TopProductDocumentsDto> result = new ArrayList<>();
        int count = 0;
        for (Object[] row : rows) {
            if (count >= topN) break;

            String policyId = (String) row[0];
            long totalDocs = ((Number) row[1]).longValue();
            long avgSize = row[2] != null ? ((Number) row[2]).longValue() : 0L;

            DimPolicy policy = policyRepo.findByPolicyId(policyId);

            result.add(new TopProductDocumentsDto(
                    policy != null ? policy.getProductId() : "UNKNOWN",
                    policy != null ? policy.getProductName() : "Unknown Product",
                    totalDocs,
                    avgSize
            ));
            count++;
        }

        return result;
    }
}
