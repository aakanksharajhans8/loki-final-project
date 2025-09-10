package com.example.eventcollector.service;

import com.example.eventcollector.dto.UiEventDto;
import com.example.eventcollector.model.BronzeEvent;
import com.example.eventcollector.model.GoldUserSession;
import com.example.eventcollector.model.SilverEvent;
import com.example.eventcollector.repository.BronzeEventRepository;
import com.example.eventcollector.repository.GoldUserSessionRepository;
import com.example.eventcollector.repository.SilverEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class EtlService {

    private final BronzeEventRepository bronzeRepo;
    private final SilverEventRepository silverRepo;
    private final GoldUserSessionRepository goldRepo;
    private final ObjectMapper objectMapper;

    public EtlService(BronzeEventRepository bronzeRepo, SilverEventRepository silverRepo, GoldUserSessionRepository goldRepo, ObjectMapper objectMapper) {
        this.bronzeRepo = bronzeRepo;
        this.silverRepo = silverRepo;
        this.goldRepo = goldRepo;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    @Transactional
    public void processBronzeToSilverAndGold() {
        System.out.println("Running ETL process...");
        List<BronzeEvent> unprocessedEvents = bronzeRepo.findTop100ByProcessedIsFalse();

        if (unprocessedEvents.isEmpty()) {
            System.out.println("No new events to process.");
            return;
        }

        System.out.printf("Found %d new events to process.%n", unprocessedEvents.size());

        for (BronzeEvent bronzeEvent : unprocessedEvents) {
            try {
                // BRONZE -> SILVER: Parse, Clean, and Structure
                UiEventDto dto = objectMapper.readValue(bronzeEvent.getPayload(), UiEventDto.class);

                // Basic validation
                if (dto.getUserId() == null || dto.getSessionId() == null) {
                    bronzeEvent.setProcessed(true); // Mark as processed to avoid retrying bad data
                    continue;
                }

                SilverEvent silverEvent = new SilverEvent();
                silverEvent.setUserId(dto.getUserId());
                silverEvent.setSessionId(dto.getSessionId());
                silverEvent.setEventType(dto.getEvent());
                silverEvent.setPage(dto.getPage());
                silverEvent.setEventTimestamp(dto.getTimestamp());

                if (dto.getMetadata() != null && dto.getMetadata().get("elementId") != null) {
                    silverEvent.setElementId(dto.getMetadata().get("elementId").toString());
                }

                silverRepo.save(silverEvent);

                // SILVER -> GOLD: Aggregate session data
                GoldUserSession session = goldRepo.findById(dto.getSessionId()).orElse(new GoldUserSession());

                if (session.getSessionId() == null) { // New session
                    session.setSessionId(dto.getSessionId());
                    session.setUserId(dto.getUserId());
                    session.setSessionStart(dto.getTimestamp());
                    session.setEventCount(0);
                }

                session.setSessionEnd(dto.getTimestamp());
                session.setEventCount(session.getEventCount() + 1);

                if ("buy_now_click".equals(dto.getEvent())) {
                    session.setConverted(true);
                }

                goldRepo.save(session);

                bronzeEvent.setProcessed(true);
            } catch (Exception e) {
                System.err.println("Failed to process bronze event ID " + bronzeEvent.getId() + ": " + e.getMessage());
                bronzeEvent.setProcessed(true); // Mark as processed to avoid poison pill messages
            }
        }
        bronzeRepo.saveAll(unprocessedEvents);
        System.out.println("ETL process finished.");
    }
}