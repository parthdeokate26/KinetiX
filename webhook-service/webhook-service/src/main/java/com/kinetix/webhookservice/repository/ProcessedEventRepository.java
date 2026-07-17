package com.kinetix.webhookservice.repository;

import com.kinetix.webhookservice.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {

    Optional<ProcessedEvent> findByEventIdAndWorkflowId(String eventId, Long workflowId);

}