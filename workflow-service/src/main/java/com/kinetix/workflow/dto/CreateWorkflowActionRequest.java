package com.kinetix.workflow.dto;

import com.kinetix.workflow.entity.ActionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWorkflowActionRequest {

    @NotNull(message = "Action type is required")
    private ActionType actionType;

    @NotNull(message = "Execution order is required")
    private Integer executionOrder;

    @NotNull(message = "Configuration is required")
    private String configuration;

}