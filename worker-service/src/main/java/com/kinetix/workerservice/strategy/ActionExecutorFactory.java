package com.kinetix.workerservice.strategy;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActionExecutorFactory {

    private final Map<String, ActionExecutor> executorMap = new HashMap<>();

    private final List<ActionExecutor> executors;

    public ActionExecutorFactory(List<ActionExecutor> executors) {
        this.executors = executors;
    }

    @PostConstruct
    public void init() {

        for (ActionExecutor executor : executors) {
            executorMap.put(executor.getActionType(), executor);
        }

    }

    public ActionExecutor getExecutor(String actionType) {

        ActionExecutor executor = executorMap.get(actionType);

        if (executor == null) {
            throw new IllegalArgumentException(
                    "No executor found for action type: " + actionType);
        }

        return executor;
    }

}