package com.insurance.documents.backend.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.insurance.documents.backend.constants.ApiConstants;
import com.insurance.documents.backend.dto.FileDownloadDto;
import com.insurance.documents.backend.exception.InvalidFileTypeException;
import com.insurance.documents.backend.exception.ResourceNotFoundException;
import com.insurance.documents.backend.exception.StorageException;
import com.insurance.documents.backend.model.DocumentMetadata;
import com.insurance.documents.backend.model.DocumentType;
import com.insurance.documents.backend.repository.DocumentMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GcsStorageService implements StorageService {

    private final Storage storage;
    private final String bucketName;
    private final DocumentMetadataRepository metadataRepository;

    public GcsStorageService(Storage storage,
                             @Value("${gcs.bucket.name}") String bucketName,
                             DocumentMetadataRepository metadataRepository) {
        this.storage = storage;
        this.bucketName = bucketName;
        this.metadataRepository = metadataRepository;
    }

    @Override
    @Transactional
    public DocumentMetadata uploadFile(MultipartFile file, String userId, DocumentType documentType, String referenceId) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        if (!isPdf(file)) {
            throw new InvalidFileTypeException("Only PDF files are allowed.");
        }

        String gcsFilename = generateUniqueFileName(file.getOriginalFilename());

        try {
            BlobId blobId = BlobId.of(bucketName, gcsFilename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            storage.create(blobInfo, file.getBytes());

            DocumentMetadata metadata = new DocumentMetadata(userId, documentType, referenceId, gcsFilename, file.getOriginalFilename());
            return metadataRepository.save(metadata);

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileDownloadDto downloadFile(String referenceId, DocumentType documentType) {
        DocumentMetadata metadata = metadataRepository.findByReferenceIdAndDocumentType(referenceId, documentType)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for reference ID: " + referenceId + " and type: " + documentType));

        try {
            Blob blob = storage.get(bucketName, metadata.getGcsFilename());
            if (blob == null) {
                throw new StorageException("File not found in GCS: " + metadata.getGcsFilename() + ". Database metadata may be out of sync.");
            }
            byte[] content = blob.getContent();
            return new FileDownloadDto(content, metadata.getOriginalFilename());
        } catch (Exception e) {
            throw new StorageException("Failed to download file: " + metadata.getGcsFilename(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentMetadata> getDocumentsByUserId(String userId) {
        List<DocumentMetadata> userDocuments = metadataRepository.findAllByUserId(userId);
        if (userDocuments.isEmpty()) {
            throw new ResourceNotFoundException("User not found or has no documents.");
        }
        return userDocuments;
    }

    private boolean isPdf(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals(ApiConstants.PDF_CONTENT_TYPE);
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString() + ApiConstants.FILENAME_SEPARATOR + originalFilename;
    }
}
