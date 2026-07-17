package com.kinetix.workflow.controller;

import com.kinetix.workflow.dto.CreateWorkflowRequest;
import com.kinetix.workflow.dto.InternalWorkflowResponse;
import com.kinetix.workflow.dto.WorkflowResponse;
import com.kinetix.workflow.service.WorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    public WorkflowResponse createWorkflow(
            @Valid @RequestBody CreateWorkflowRequest request,
            Authentication authentication) {

        return workflowService.createWorkflow(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<WorkflowResponse> getAllWorkflows(
            Authentication authentication) {

        return workflowService.getAllWorkflows(
                authentication.getName()
        );
    }

    @GetMapping("/{id}")
    public WorkflowResponse getWorkflowById(
            @PathVariable Long id,
            Authentication authentication) {

        return workflowService.getWorkflowById(
                id,
                authentication.getName()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteWorkflow(
            @PathVariable Long id,
            Authentication authentication) {

        workflowService.deleteWorkflow(
                id,
                authentication.getName()
        );
    }

    @PatchMapping("/{id}/enable")
    public WorkflowResponse enableWorkflow(
            @PathVariable Long id,
            Authentication authentication) {

        return workflowService.enableWorkflow(
                id,
                authentication.getName()
        );
    }
    @GetMapping("/internal/{id}")
    public WorkflowResponse getWorkflowInternal(@PathVariable Long id) {
        return workflowService.getWorkflowInternal(id);
    }

    @PatchMapping("/{id}/disable")
    public WorkflowResponse disableWorkflow(
            @PathVariable Long id,
            Authentication authentication) {

        return workflowService.disableWorkflow(
                id,
                authentication.getName()
        );
    }
}