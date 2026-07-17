package com.kinetix.webhookservice.dto;

import com.kinetix.webhookservice.entity.ExecutionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookResponse {

    private String message;
}