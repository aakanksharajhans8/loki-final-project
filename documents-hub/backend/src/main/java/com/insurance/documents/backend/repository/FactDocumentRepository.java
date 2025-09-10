package com.insurance.documents.backend.repository;



import com.insurance.documents.backend.model.FactDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FactDocumentRepository extends JpaRepository<FactDocument, Long> {

    // Daily document stats (for analytics dashboard)
    @Query("""
        SELECT f.dateKey AS date,
               COUNT(f.id) AS totalUploads,
               SUM(CASE WHEN f.isPdf = true THEN 1 ELSE 0 END) AS pdfGenerated,
               AVG(f.sizeBytes) AS avgSizeBytes
        FROM FactDocument f
        WHERE f.dateKey BETWEEN :start AND :end
        GROUP BY f.dateKey
        ORDER BY f.dateKey
    """)
    List<Object[]> findDailyStats(@Param("start") LocalDate startDate,
                                  @Param("end") LocalDate endDate);

    // Documents by policy
    List<FactDocument> findByPolicyIdOrderByUploadedAtDesc(String policyId);

    // Top products (will join with DimPolicy in service)
    @Query("""
        SELECT f.policyId AS policyId, COUNT(f.id) AS totalDocs, AVG(f.sizeBytes) AS avgSize
        FROM FactDocument f
        WHERE f.dateKey BETWEEN :start AND :end
        GROUP BY f.policyId
        ORDER BY COUNT(f.id) DESC
    """)
    List<Object[]> findTopProducts(@Param("start") LocalDate startDate,
                                   @Param("end") LocalDate endDate);
}

