package com.kinetix.workflow.service.impl;

import com.kinetix.workflow.dto.CreateWorkflowRequest;
import com.kinetix.workflow.dto.WorkflowActionResponse;
import com.kinetix.workflow.dto.WorkflowResponse;
import com.kinetix.workflow.entity.User;
import com.kinetix.workflow.entity.Workflow;
import com.kinetix.workflow.entity.WorkflowAction;
import com.kinetix.workflow.repository.UserRepository;
import com.kinetix.workflow.repository.WorkflowActionRepository;
import com.kinetix.workflow.repository.WorkflowRepository;
import com.kinetix.workflow.service.WorkflowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowActionRepository workflowActionRepository;
    private final UserRepository userRepository;

    @Override
    public WorkflowResponse createWorkflow(CreateWorkflowRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Workflow workflow = Workflow.builder()
                .name(request.getName())
                .description(request.getDescription())
                .enabled(true)
                .webhookSecret(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        return mapToResponse(workflowRepository.save(workflow));
    }
    @Override
    public WorkflowResponse getWorkflowInternal(Long id) {

        Workflow workflow = workflowRepository.findById(id)
                .orElse(null);

        if (workflow == null) {
            return null;
        }

        WorkflowResponse response = mapToResponse(workflow);

        List<WorkflowActionResponse> actions =
                workflowActionRepository
                        .findByWorkflowIdOrderByExecutionOrderAsc(id)
                        .stream()
                        .map(this::mapActionToResponse)
                        .toList();

        response.setActions(actions);

        return response;
    }

    @Override
    public List<WorkflowResponse> getAllWorkflows(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        return workflowRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WorkflowResponse getWorkflowById(Long workflowId, String username) {

        Workflow workflow = getUserWorkflow(workflowId, username);

        return mapToResponse(workflow);
    }

    @Override
    public void deleteWorkflow(Long workflowId, String username) {

        Workflow workflow = getUserWorkflow(workflowId, username);

        workflowRepository.delete(workflow);
    }

    @Override
    public WorkflowResponse enableWorkflow(Long workflowId, String username) {

        Workflow workflow = getUserWorkflow(workflowId, username);

        workflow.setEnabled(true);
        workflow.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(workflowRepository.save(workflow));
    }

    @Override
    public WorkflowResponse disableWorkflow(Long workflowId, String username) {

        Workflow workflow = getUserWorkflow(workflowId, username);

        workflow.setEnabled(false);
        workflow.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(workflowRepository.save(workflow));
    }

    private Workflow getUserWorkflow(Long workflowId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Workflow not found"));

        if (!workflow.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to access this workflow");
        }

        return workflow;
    }

    private WorkflowResponse mapToResponse(Workflow workflow) {

        return WorkflowResponse.builder()
                .id(workflow.getId())
                .name(workflow.getName())
                .description(workflow.getDescription())
                .enabled(workflow.getEnabled())
                .webhookSecret(workflow.getWebhookSecret())
                .createdAt(workflow.getCreatedAt())
                .updatedAt(workflow.getUpdatedAt())
                .build();
    }

    private WorkflowActionResponse mapActionToResponse(
            WorkflowAction action) {

        WorkflowActionResponse response = new WorkflowActionResponse();

        response.setId(action.getId());
        response.setExecutionOrder(action.getExecutionOrder());
        response.setActionType(action.getActionType());
        response.setConfiguration(action.getConfiguration());

        return response;
    }
}