package com.kinetix.webhookservice.repository;

import com.kinetix.webhookservice.entity.WorkflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecution, Long> {
}