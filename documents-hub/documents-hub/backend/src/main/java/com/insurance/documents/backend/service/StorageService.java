package com.insurance.documents.backend.service;

import com.insurance.documents.backend.dto.FileDownloadDto;
import com.insurance.documents.backend.model.DocumentMetadata;
import com.insurance.documents.backend.model.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    /**
     * Uploads a file to storage and saves its metadata to the database.
     *
     * @param file         the file to upload
     * @param userId       the ID of the user owning the document
     * @param documentType the type of the document
     * @param referenceId  the business reference ID (e.g., policy number)
     * @return the saved document metadata
     */
    DocumentMetadata uploadFile(MultipartFile file, String userId, DocumentType documentType, String referenceId);

    /**
     * Downloads a file from storage by its business reference.
     *
     * @param referenceId  the business reference ID
     * @param documentType the type of the document
     * @return a DTO containing the file content and original filename
     */
    FileDownloadDto downloadFile(String referenceId, DocumentType documentType);

    /**
     * Retrieves all document metadata for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the user's document metadata
     */
    List<DocumentMetadata> getDocumentsByUserId(String userId);

}
