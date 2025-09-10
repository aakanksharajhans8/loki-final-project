package com.insurance.underwriting.exception;

import lombok.*;
import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, Object> details;
}
