package com.kinetix.webhookservice.service;

import com.kinetix.webhookservice.dto.WorkflowResponse;

public interface WorkflowClient {

    WorkflowResponse getWorkflow(Long workflowId);

}