package com.kinetix.workflow.service.impl;

import com.kinetix.workflow.dto.CreateWorkflowActionRequest;
import com.kinetix.workflow.dto.WorkflowActionResponse;
import com.kinetix.workflow.entity.User;
import com.kinetix.workflow.entity.Workflow;
import com.kinetix.workflow.entity.WorkflowAction;
import com.kinetix.workflow.repository.UserRepository;
import com.kinetix.workflow.repository.WorkflowActionRepository;
import com.kinetix.workflow.repository.WorkflowRepository;
import com.kinetix.workflow.service.WorkflowActionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowActionServiceImpl implements WorkflowActionService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowActionRepository workflowActionRepository;
    private final UserRepository userRepository;

    @Override
    public WorkflowActionResponse addAction(
            Long workflowId,
            CreateWorkflowActionRequest request,
            String username) {

        Workflow workflow = getUserWorkflow(workflowId, username);

        WorkflowAction action = WorkflowAction.builder()
                .actionType(request.getActionType())
                .executionOrder(request.getExecutionOrder())
                .configuration(request.getConfiguration())
                .workflow(workflow)
                .build();

        return mapToResponse(
                workflowActionRepository.save(action)
        );
    }

    @Override
    public List<WorkflowActionResponse> getActions(
            Long workflowId,
            String username) {

        getUserWorkflow(workflowId, username);

        return workflowActionRepository
                .findByWorkflowIdOrderByExecutionOrderAsc(workflowId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAction(
            Long actionId,
            String username) {

        WorkflowAction action = workflowActionRepository.findById(actionId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Action not found"));

        if (!action.getWorkflow()
                .getUser()
                .getUsername()
                .equals(username)) {

            throw new RuntimeException(
                    "You are not allowed to delete this action");
        }

        workflowActionRepository.delete(action);
    }

    private Workflow getUserWorkflow(
            Long workflowId,
            String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Workflow not found"));

        if (!workflow.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "Unauthorized workflow access");
        }

        return workflow;
    }

    private WorkflowActionResponse mapToResponse(
            WorkflowAction action) {

        return WorkflowActionResponse.builder()
                .id(action.getId())
                .actionType(action.getActionType())
                .executionOrder(action.getExecutionOrder())
                .configuration(action.getConfiguration())
                .build();
    }

}