package com.kinetix.workerservice.dto;

import lombok.Data;

@Data
public class WorkflowActionDto {

    private Long id;

    private Integer executionOrder;

    private String actionType;

    private String configuration;

}