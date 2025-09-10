package com.example.eventcollector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "gold_user_sessions")
@Getter
@Setter
public class GoldUserSession {
    @Id
    private String sessionId; // We use the session ID as the primary key

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Instant sessionStart;

    private Instant sessionEnd;

    private long eventCount;

    private boolean converted = false; // Example: Did they click a "buy" button?
}