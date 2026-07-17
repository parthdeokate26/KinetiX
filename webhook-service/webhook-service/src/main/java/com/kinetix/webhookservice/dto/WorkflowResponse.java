package com.kinetix.webhookservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowResponse {

    private Long id;
    private String name;
    private boolean enabled;
}