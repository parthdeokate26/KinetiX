package com.kinetix.workflow.controller;

import com.kinetix.workflow.dto.CreateWorkflowActionRequest;
import com.kinetix.workflow.dto.WorkflowActionResponse;
import com.kinetix.workflow.service.WorkflowActionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflows/{workflowId}/actions")
@RequiredArgsConstructor
public class WorkflowActionController {

    private final WorkflowActionService workflowActionService;

    @PostMapping
    public WorkflowActionResponse addAction(
            @PathVariable Long workflowId,
            @Valid @RequestBody CreateWorkflowActionRequest request,
            Authentication authentication) {

        return workflowActionService.addAction(
                workflowId,
                request,
                authentication.getName());
    }

    @GetMapping
    public List<WorkflowActionResponse> getActions(
            @PathVariable Long workflowId,
            Authentication authentication) {

        return workflowActionService.getActions(
                workflowId,
                authentication.getName());
    }

    @DeleteMapping("/{actionId}")
    public void deleteAction(
            @PathVariable Long workflowId,
            @PathVariable Long actionId,
            Authentication authentication) {

        workflowActionService.deleteAction(
                actionId,
                authentication.getName());
    }

}