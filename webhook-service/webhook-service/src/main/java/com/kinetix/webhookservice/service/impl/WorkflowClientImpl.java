package com.kinetix.webhookservice.service.impl;

import com.kinetix.webhookservice.dto.WorkflowResponse;
import com.kinetix.webhookservice.service.WorkflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WorkflowClientImpl implements WorkflowClient {

    private final RestTemplate restTemplate;

    private static final String WORKFLOW_SERVICE_URL =
            "http://localhost:8080/workflows/internal/";

    @Override
    public WorkflowResponse getWorkflow(Long workflowId) {

        return restTemplate.getForObject(
                WORKFLOW_SERVICE_URL + workflowId,
                WorkflowResponse.class
        );

    }
}