package com.kinetix.workerservice.repository;

import com.kinetix.workerservice.entity.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {
}