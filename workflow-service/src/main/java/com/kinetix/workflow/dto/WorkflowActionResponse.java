package com.kinetix.workflow.dto;

import com.kinetix.workflow.entity.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowActionResponse {

    private Long id;

    private ActionType actionType;

    private Integer executionOrder;

    private String configuration;

}