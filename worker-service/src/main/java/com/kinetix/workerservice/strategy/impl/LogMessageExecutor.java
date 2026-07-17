package com.kinetix.workerservice.strategy.impl;

import com.kinetix.workerservice.entity.WorkflowExecution;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import com.kinetix.workerservice.dto.WorkflowActionDto;
import com.kinetix.workerservice.strategy.ActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogMessageExecutor implements ActionExecutor {

    private final ObjectMapper objectMapper;

    @Override
    public String getActionType() {
        return "LOG_MESSAGE";
    }

    @Override
    public void execute(WorkflowActionDto action, WorkflowExecution execution) {

        try {

            JsonNode json =
                    objectMapper.readTree(action.getConfiguration());

            String message =
                    json.get("message").asString();

            log.info(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}