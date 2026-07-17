package com.kinetix.workflow.service;

import com.kinetix.workflow.dto.CreateWorkflowActionRequest;
import com.kinetix.workflow.dto.WorkflowActionResponse;

import java.util.List;

public interface WorkflowActionService {

    WorkflowActionResponse addAction(
            Long workflowId,
            CreateWorkflowActionRequest request,
            String username);

    List<WorkflowActionResponse> getActions(
            Long workflowId,
            String username);

    void deleteAction(
            Long actionId,
            String username);

}