package com.kinetix.workerservice.consumer;

import com.kinetix.workerservice.dto.ExecutionEvent;
import com.kinetix.workerservice.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionEventConsumer {

    private final WorkerService workerService;

    @KafkaListener(
            topics = "workflow-execution-topic")
    public void consume(ExecutionEvent event) {

        workerService.processExecution(event);

    }
}