package com.kinetix.workerservice.client;

import com.kinetix.workerservice.dto.WorkflowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WorkflowClient {

    private final RestTemplate restTemplate;

    @Value("${workflow.service.url}")
    private String workflowServiceUrl;

    public WorkflowDto getWorkflow(Long workflowId) {

        String url = workflowServiceUrl + "/workflows/internal/" + workflowId;

        return restTemplate.getForObject(url, WorkflowDto.class);
    }

}