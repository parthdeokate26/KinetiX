package com.kinetix.webhookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionEvent {

    private Long workflowId;

    private String payload;

    private String eventId;

}