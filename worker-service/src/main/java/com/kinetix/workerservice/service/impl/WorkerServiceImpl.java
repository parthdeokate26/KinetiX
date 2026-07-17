package com.kinetix.workerservice.service.impl;

import com.kinetix.workerservice.dto.ExecutionEvent;
import com.kinetix.workerservice.entity.ExecutionStatus;
import com.kinetix.workerservice.entity.WorkflowExecution;
import com.kinetix.workerservice.repository.WorkflowExecutionRepository;
import com.kinetix.workerservice.service.ExecutionService;
import com.kinetix.workerservice.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final ExecutionService executionService;

    @Override
    public void processExecution(ExecutionEvent event) {

        WorkflowExecution execution = WorkflowExecution.builder()
                .workflowId(event.getWorkflowId())
                .payload(event.getPayload())
                .status(ExecutionStatus.RUNNING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .build();

        execution = workflowExecutionRepository.save(execution);

        executionService.processExecution(execution);

    }
}