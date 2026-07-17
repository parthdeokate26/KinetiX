package com.kinetix.webhookservice.service;

import com.kinetix.webhookservice.dto.WebhookResponse;

public interface WebhookService {

    WebhookResponse receiveWebhook(Long workflowId, String payload);

}