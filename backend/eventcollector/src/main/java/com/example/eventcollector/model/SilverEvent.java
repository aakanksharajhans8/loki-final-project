package com.example.eventcollector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "silver_events")
@Getter
@Setter
public class SilverEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String page;

    private String elementId;

    @Column(nullable = false)
    private Instant eventTimestamp;
}