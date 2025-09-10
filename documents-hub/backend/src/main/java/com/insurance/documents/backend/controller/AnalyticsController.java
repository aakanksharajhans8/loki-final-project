package com.insurance.documents.backend.controller;

import com.insurance.documents.backend.dto.DailyDocumentStatsDto;
import com.insurance.documents.backend.dto.PolicyDocumentsDto;
import com.insurance.documents.backend.dto.TopProductDocumentsDto;
import com.insurance.documents.backend.service.AnalyticsService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/documents/daily")
    public ResponseEntity<List<DailyDocumentStatsDto>> daily(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<DailyDocumentStatsDto> rows = analyticsService.getDailyDocumentStats(startDate, endDate);
        return ResponseEntity.ok(rows);
    }

    @GetMapping("/documents/by-policy/{policyId}")
    public ResponseEntity<PolicyDocumentsDto> byPolicy(
            @PathVariable @NotBlank String policyId
    ) {
        PolicyDocumentsDto dto = analyticsService.getDocumentsByPolicy(policyId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/documents/top-products")
    public ResponseEntity<List<TopProductDocumentsDto>> topProducts(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "10") int topN
    ) {
        List<TopProductDocumentsDto> rows = analyticsService.getTopProducts(startDate, endDate, Math.max(1, topN));
        return ResponseEntity.ok(rows);
    }
}
