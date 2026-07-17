package com.kinetix.workerservice.service;

import com.kinetix.workerservice.dto.WorkflowActionDto;
import com.kinetix.workerservice.entity.WorkflowExecution;
import org.springframework.stereotype.Component;

@Component
public interface ExecutionService {

    void processExecution(WorkflowExecution execution);

}