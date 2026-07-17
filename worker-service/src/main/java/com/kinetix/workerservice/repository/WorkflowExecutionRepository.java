package com.kinetix.workerservice.repository;

import com.kinetix.workerservice.entity.ExecutionStatus;
import com.kinetix.workerservice.entity.WorkflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecution, Long> {

    List<WorkflowExecution> findByStatus(ExecutionStatus status);

}