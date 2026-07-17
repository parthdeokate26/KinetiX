package com.kinetix.workflow.repository;

import com.kinetix.workflow.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    List<Workflow> findByUserId(Long userId);
}