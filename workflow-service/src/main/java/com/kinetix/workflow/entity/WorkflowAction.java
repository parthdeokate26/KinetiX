package com.kinetix.workflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;

    @Column(nullable = false)
    private Integer executionOrder;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String configuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;
}