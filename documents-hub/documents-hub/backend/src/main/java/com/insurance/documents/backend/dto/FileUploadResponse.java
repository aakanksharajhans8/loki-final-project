package com.insurance.documents.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    private String originalFilename;
    private String fileId; // Changed from fileUrl to reflect the unique filename
    private String message;

}
