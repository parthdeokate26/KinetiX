package com.kinetix.workflow.repository;

import com.kinetix.workflow.entity.WorkflowAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowActionRepository extends JpaRepository<WorkflowAction, Long> {

    List<WorkflowAction> findByWorkflowIdOrderByExecutionOrderAsc(Long workflowId);
}