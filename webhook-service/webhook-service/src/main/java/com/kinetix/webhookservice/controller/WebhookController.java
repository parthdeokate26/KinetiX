package com.kinetix.webhookservice.controller;

import com.kinetix.webhookservice.dto.WebhookResponse;
import com.kinetix.webhookservice.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/{workflowId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WebhookResponse receiveWebhook(
            @PathVariable Long workflowId,
            @RequestBody String payload
    ) {
        return webhookService.receiveWebhook(workflowId, payload);
    }
}