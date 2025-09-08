package com.insurance.documents.backend.repository;

import com.insurance.documents.backend.model.DocumentMetadata;
import com.insurance.documents.backend.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {

    /**
     * Finds document metadata by the business reference ID and document type.
     *
     * @param referenceId  the business reference ID (e.g., policy number)
     * @param documentType the type of the document
     * @return an Optional containing the document metadata if found
     */
    Optional<DocumentMetadata> findByReferenceIdAndDocumentType(String referenceId, DocumentType documentType);

    /**
     * Finds all document metadata associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of document metadata for the user
     */
    List<DocumentMetadata> findAllByUserId(String userId);

}
