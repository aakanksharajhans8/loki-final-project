package com.example.eventcollector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data // Lombok annotation for getters, setters, toString, etc.
public class UiEventDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String event;
    @NotBlank
    private String page;
    private Instant timestamp;
    private String sessionId;
    private Map<String, Object> metadata;
}