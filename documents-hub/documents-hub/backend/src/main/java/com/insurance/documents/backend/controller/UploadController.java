package com.insurance.documents.backend.controller;

import com.insurance.documents.backend.constants.ApiConstants;
import com.insurance.documents.backend.dto.FileDownloadDto;
import com.insurance.documents.backend.model.DocumentMetadata;
import com.insurance.documents.backend.model.DocumentType;
import com.insurance.documents.backend.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH)
public class UploadController {

    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Operation(summary = "Upload a document and associate it with a user and business entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document uploaded and metadata created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, file type, or empty file"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = ApiConstants.UPLOAD_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentMetadata> uploadFile(
            @Parameter(description = "The file to upload (must be a PDF)", required = true) @RequestParam("file") MultipartFile file,
            @Parameter(description = "The ID of the user who owns this document", required = true) @RequestParam("userId") String userId,
            @Parameter(description = "The type of the document (e.g., POLICY_DOCUMENT, CLAIM_RECEIPT)", required = true) @RequestParam("documentType") DocumentType documentType,
            @Parameter(description = "The business reference ID (e.g., policy number, claim ID)", required = true) @RequestParam("referenceId") String referenceId) {

        DocumentMetadata metadata = storageService.uploadFile(file, userId, documentType, referenceId);
        return new ResponseEntity<>(metadata, HttpStatus.CREATED);
    }

    @Operation(summary = "Download a document by its business reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "Document not found for the given reference"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(
            @Parameter(description = "The business reference ID (e.g., policy number, claim ID)", required = true) @RequestParam("referenceId") String referenceId,
            @Parameter(description = "The type of the document to download", required = true) @RequestParam("documentType") DocumentType documentType) {

        FileDownloadDto fileDownloadDto = storageService.downloadFile(referenceId, documentType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ApiConstants.PDF_CONTENT_TYPE));
        headers.setContentDispositionFormData(ApiConstants.CONTENT_DISPOSITION_ATTACHMENT, fileDownloadDto.getOriginalFilename());

        return new ResponseEntity<>(fileDownloadDto.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Get all document metadata for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of documents"),
            @ApiResponse(responseCode = "404", description = "User not found or has no documents")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentMetadata>> getDocumentsByUserId(
            @Parameter(description = "The ID of the user whose documents are to be retrieved")
            @PathVariable String userId) {
        List<DocumentMetadata> documents = storageService.getDocumentsByUserId(userId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
}
