package com.insurance.documents.backend.constants;

public final class ApiConstants {

    // Private constructor to prevent instantiation
    private ApiConstants() {}

    // API Paths
    public static final String API_BASE_PATH = "/api/documents";
    public static final String UPLOAD_PATH = "/upload";
    public static final String DOWNLOAD_PATH = "/download/{filename}";

    // HTTP Content Types
    public static final String PDF_CONTENT_TYPE = "application/pdf";

    // File-related constants
    public static final String FILENAME_SEPARATOR = "_";
    public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment";

}
