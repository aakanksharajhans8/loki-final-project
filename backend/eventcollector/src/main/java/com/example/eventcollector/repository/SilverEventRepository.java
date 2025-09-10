package com.example.eventcollector.repository;

import com.example.eventcollector.model.SilverEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SilverEventRepository extends JpaRepository<SilverEvent, Long> {
}