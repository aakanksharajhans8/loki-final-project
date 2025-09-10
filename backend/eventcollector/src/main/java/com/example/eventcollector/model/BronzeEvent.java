package com.example.eventcollector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "bronze_events")
@Getter
@Setter
public class BronzeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Large Object, for storing large text like JSON
    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private Instant receivedAt;

    private boolean processed = false;
}