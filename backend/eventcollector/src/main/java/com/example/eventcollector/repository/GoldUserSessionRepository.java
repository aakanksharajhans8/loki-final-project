package com.example.eventcollector.repository;

import com.example.eventcollector.model.GoldUserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldUserSessionRepository extends JpaRepository<GoldUserSession, String> {
}