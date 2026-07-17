package com.kinetix.webhookservice.service.impl;

import com.kinetix.webhookservice.dto.ExecutionEvent;
import com.kinetix.webhookservice.dto.WebhookResponse;
import com.kinetix.webhookservice.dto.WorkflowResponse;
import com.kinetix.webhookservice.entity.ExecutionStatus;
import com.kinetix.webhookservice.entity.ProcessedEvent;
import com.kinetix.webhookservice.entity.WorkflowExecution;
import com.kinetix.webhookservice.exception.InvalidWebhookPayloadException;
import com.kinetix.webhookservice.exception.WorkflowDisabledException;
import com.kinetix.webhookservice.exception.WorkflowNotFoundException;
import com.kinetix.webhookservice.producer.ExecutionEventProducer;
import com.kinetix.webhookservice.repository.ProcessedEventRepository;
import com.kinetix.webhookservice.repository.WorkflowExecutionRepository;
import com.kinetix.webhookservice.service.WebhookService;
import com.kinetix.webhookservice.service.WorkflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final WorkflowClient workflowClient;
    private final ProcessedEventRepository processedEventRepository;
    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final ObjectMapper objectMapper;
    private final ExecutionEventProducer executionEventProducer;
    @Override
    public WebhookResponse receiveWebhook(Long workflowId, String payload) {

        WorkflowResponse workflow = workflowClient.getWorkflow(workflowId);

        if (workflow == null) {
            throw new WorkflowNotFoundException("Workflow not found.");
        }

        if (!workflow.isEnabled()) {
            throw new WorkflowDisabledException("Workflow is disabled");
        }

        try {

            JsonNode jsonNode = objectMapper.readTree(payload);

            JsonNode eventIdNode = jsonNode.get("eventId");

            if (eventIdNode == null) {
                throw new RuntimeException("eventId is missing.");
            }

            String eventId = eventIdNode.asString();

            var processedEvent = processedEventRepository
                    .findByEventIdAndWorkflowId(eventId, workflowId);

            if (processedEvent.isPresent()) {

                return WebhookResponse.builder()
                        .message("Webhook already received.")
                        .build();
            }

            ExecutionEvent event = ExecutionEvent.builder()
                    .workflowId(workflowId)
                    .eventId(eventId)
                    .payload(payload)
                    .build();

            executionEventProducer.publish(event);
            ProcessedEvent processedEventEntity = ProcessedEvent.builder()
                    .eventId(eventId)
                    .workflowId(workflowId)
                    .receivedAt(LocalDateTime.now())
                    .build();

            processedEventRepository.save(processedEventEntity);

            return WebhookResponse.builder()
                    .message("Webhook accepted successfully.")
                    .build();
        } catch (Exception e) {
            throw new InvalidWebhookPayloadException("Invalid webhook payload.");
        }
    }
}