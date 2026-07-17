package com.kinetix.workflow.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WorkflowResponse {

    private Long id;

    private String name;

    private String description;

    private Boolean enabled;

    private String webhookSecret;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private List<WorkflowActionResponse> actions;

}