package com.insurance.documents.backend.exception;

import com.insurance.documents.backend.dto.FileUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<FileUploadResponse> handleStorageException(StorageException ex) {
        FileUploadResponse response = new FileUploadResponse("", "", "Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<FileUploadResponse> handleInvalidFileTypeException(InvalidFileTypeException ex) {
        FileUploadResponse response = new FileUploadResponse("", "", "Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<FileUploadResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        FileUploadResponse response = new FileUploadResponse("", "", "Error: File size exceeds the maximum limit!");
        return new ResponseEntity<>(response, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
