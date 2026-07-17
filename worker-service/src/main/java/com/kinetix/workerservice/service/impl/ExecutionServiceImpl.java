package com.kinetix.workerservice.service.impl;

import com.kinetix.workerservice.client.WorkflowClient;
import com.kinetix.workerservice.dto.WorkflowActionDto;
import com.kinetix.workerservice.dto.WorkflowDto;
import com.kinetix.workerservice.entity.ExecutionLog;
import com.kinetix.workerservice.entity.ExecutionStatus;
import com.kinetix.workerservice.entity.LogStatus;
import com.kinetix.workerservice.entity.WorkflowExecution;
import com.kinetix.workerservice.repository.ExecutionLogRepository;
import com.kinetix.workerservice.repository.WorkflowExecutionRepository;
import com.kinetix.workerservice.service.ExecutionService;
import com.kinetix.workerservice.strategy.ActionExecutor;
import com.kinetix.workerservice.strategy.ActionExecutorFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {

    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final ExecutionLogRepository executionLogRepository;
    private final WorkflowClient workflowClient;
    private final ActionExecutorFactory actionExecutorFactory;

    @Override
    @Transactional
    public void processExecution(WorkflowExecution execution) {

        try {


            // Fetch workflow and actions
            WorkflowDto workflow =
                    workflowClient.getWorkflow(execution.getWorkflowId());

            log.info("Executing Workflow : {}", workflow.getName());

            // Execute actions in order
            workflow.getActions()
                    .stream()
                    .sorted(Comparator.comparing(WorkflowActionDto::getExecutionOrder))
                    .forEach(action -> {

                        try {

                            ActionExecutor executor =
                                    actionExecutorFactory.getExecutor(action.getActionType());

                            executor.execute(action, execution);

                            saveExecutionLog(
                                    execution.getId(),
                                    action.getActionType(),
                                    LogStatus.SUCCESS,
                                    "Action executed successfully"
                            );

                        } catch (Exception ex) {

                            saveExecutionLog(
                                    execution.getId(),
                                    action.getActionType(),
                                    LogStatus.FAILED,
                                    ex.getMessage()
                            );

                            throw new RuntimeException(ex);
                        }

                    });

            // All actions completed
            execution.setStatus(ExecutionStatus.SUCCESS);
            execution.setCompletedAt(LocalDateTime.now());

            workflowExecutionRepository.save(execution);

            log.info("Execution {} completed successfully.", execution.getId());

        } catch (Exception ex) {

            log.error("Execution {} failed : {}", execution.getId(), ex.getMessage());

            execution.setRetryCount(execution.getRetryCount() + 1);

            execution.setStatus(ExecutionStatus.FAILED);
            execution.setCompletedAt(LocalDateTime.now());

            workflowExecutionRepository.save(execution);

            log.error("Execution {} failed.", execution.getId(), ex);
        }
    }

    private void saveExecutionLog(Long executionId,
                                  String actionName,
                                  LogStatus status,
                                  String message) {

        ExecutionLog log = ExecutionLog.builder()
                .executionId(executionId)
                .actionName(actionName)
                .status(status)
                .message(message)
                .build();

        executionLogRepository.save(log);
    }
}