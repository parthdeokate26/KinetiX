package com.kinetix.workflow.dto;

import lombok.Data;

import java.util.List;

@Data
public class InternalWorkflowResponse {

    private Long id;

    private String name;

    private String description;

    private Boolean enabled;

    private List<WorkflowActionResponse> actions;

}