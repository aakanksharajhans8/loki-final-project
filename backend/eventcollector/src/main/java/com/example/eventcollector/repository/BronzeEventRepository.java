package com.example.eventcollector.repository;

import com.example.eventcollector.model.BronzeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BronzeEventRepository extends JpaRepository<BronzeEvent, Long> {
    List<BronzeEvent> findTop100ByProcessedIsFalse();
}