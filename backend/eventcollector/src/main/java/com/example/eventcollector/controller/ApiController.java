package com.example.eventcollector.controller;

import com.example.eventcollector.dto.UiEventDto;
import com.example.eventcollector.model.BronzeEvent;
import com.example.eventcollector.model.GoldUserSession;
import com.example.eventcollector.model.SilverEvent;
import com.example.eventcollector.repository.GoldUserSessionRepository;
import com.example.eventcollector.repository.SilverEventRepository;
import com.example.eventcollector.repository.BronzeEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final BronzeEventRepository bronzeRepo;
    private final SilverEventRepository silverRepo;
    private final GoldUserSessionRepository goldRepo;
    private final ObjectMapper objectMapper;

    public ApiController(BronzeEventRepository bronzeRepo, SilverEventRepository silverRepo, GoldUserSessionRepository goldRepo, ObjectMapper objectMapper) {
        this.bronzeRepo = bronzeRepo;
        this.silverRepo = silverRepo;
        this.goldRepo = goldRepo;
        this.objectMapper = objectMapper;
    }

    // Endpoint for INGESTION (writes to Bronze)
    @PostMapping("/events")
    public ResponseEntity<Map<String, String>> ingestEvent(@Valid @RequestBody UiEventDto event) {
        try {
            if (event.getTimestamp() == null) {
                event.setTimestamp(Instant.now());
            }

            BronzeEvent bronzeEvent = new BronzeEvent();
            bronzeEvent.setPayload(objectMapper.writeValueAsString(event));
            bronzeEvent.setReceivedAt(Instant.now());
            bronzeRepo.save(bronzeEvent);

            return ResponseEntity.accepted().body(Map.of("status", "accepted"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // Endpoint for ANALYTICS (reads from Silver)
    @GetMapping("/events/silver")
    public ResponseEntity<List<SilverEvent>> getSilverEvents() {
        List<SilverEvent> events = silverRepo.findAll(PageRequest.of(0, 100, Sort.by("eventTimestamp").descending())).getContent();
        return ResponseEntity.ok(events);
    }

    // Endpoint for ANALYTICS (reads from Gold)
    @GetMapping("/analytics/sessions")
    public ResponseEntity<List<GoldUserSession>> getGoldSessions() {
        List<GoldUserSession> sessions = goldRepo.findAll(PageRequest.of(0, 100, Sort.by("sessionStart").descending())).getContent();
        return ResponseEntity.ok(sessions);
    }
}