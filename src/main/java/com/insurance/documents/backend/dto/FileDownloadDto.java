package com.insurance.documents.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDownloadDto {

    private byte[] content;
    private String originalFilename;

}
