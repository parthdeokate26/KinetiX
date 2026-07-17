package com.kinetix.workerservice.strategy;

import com.kinetix.workerservice.dto.WorkflowActionDto;
import com.kinetix.workerservice.entity.WorkflowExecution;

public interface ActionExecutor {

    String getActionType();

    void execute(WorkflowActionDto action, WorkflowExecution execution );

}