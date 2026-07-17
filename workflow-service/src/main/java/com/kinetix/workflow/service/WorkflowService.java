package com.kinetix.workflow.service;

import com.kinetix.workflow.dto.CreateWorkflowRequest;
import com.kinetix.workflow.dto.InternalWorkflowResponse;
import com.kinetix.workflow.dto.WorkflowResponse;

import java.util.List;

public interface WorkflowService {

    WorkflowResponse createWorkflow(CreateWorkflowRequest request, String username);

    WorkflowResponse getWorkflowInternal(Long id);

    List<WorkflowResponse> getAllWorkflows(String username);

    WorkflowResponse getWorkflowById(Long workflowId, String username);

    void deleteWorkflow(Long workflowId, String username);

    WorkflowResponse enableWorkflow(Long workflowId, String username);

    WorkflowResponse disableWorkflow(Long workflowId, String username);

}