package com.kinetix.workerservice.service;

import com.kinetix.workerservice.dto.ExecutionEvent;

public interface WorkerService {
    void processExecution(ExecutionEvent event);
}
