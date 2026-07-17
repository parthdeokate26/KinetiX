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
public class SaveNotificationExecutor implements ActionExecutor {

    private final ObjectMapper objectMapper;

    @Override
    public String getActionType() {
        return "SAVE_NOTIFICATION";
    }

    @Override
    public void execute(WorkflowActionDto action, WorkflowExecution execution) {

        try {

            JsonNode json = objectMapper.readTree(action.getConfiguration());

            String title = json.get("title").asString();
            String message = json.get("message").asString();

            // Version 1 - Just log it
            log.info("Notification Saved");
            log.info("Title : {}", title);
            log.info("Message : {}", message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to execute SAVE_NOTIFICATION action", e);
        }
    }
}