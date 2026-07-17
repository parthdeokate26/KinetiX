package com.kinetix.workerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowDto {

    private Long id;

    private String name;

    private Boolean enabled;

    private List<WorkflowActionDto> actions;

}