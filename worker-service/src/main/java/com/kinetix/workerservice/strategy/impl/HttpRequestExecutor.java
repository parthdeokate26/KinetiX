package com.kinetix.workerservice.strategy.impl;

import com.kinetix.workerservice.entity.WorkflowExecution;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import com.kinetix.workerservice.dto.WorkflowActionDto;
import com.kinetix.workerservice.strategy.ActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestExecutor implements ActionExecutor {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getActionType() {
        return "HTTP_REQUEST";
    }

    @Override
    public void execute(WorkflowActionDto action,
                        WorkflowExecution execution) {

        try {

            JsonNode config =
                    objectMapper.readTree(action.getConfiguration());

            String method = config.get("method").asString();
            String url = config.get("url").asString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request =
                    new HttpEntity<>(execution.getPayload(), headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.valueOf(method),
                            request,
                            String.class
                    );

            log.info("HTTP {} {} -> {}",
                    method,
                    url,
                    response.getStatusCode());

        } catch (Exception e) {
            throw new RuntimeException(
                    "HTTP Request execution failed",
                    e
            );
        }
    }
}