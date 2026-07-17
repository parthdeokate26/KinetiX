package com.kinetix.webhookservice.producer;

import com.kinetix.webhookservice.dto.ExecutionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionEventProducer {

    private static final String TOPIC = "workflow-execution-topic";

    private final KafkaTemplate<String, ExecutionEvent> kafkaTemplate;

    public void publish(ExecutionEvent event) {

        kafkaTemplate.send(TOPIC, event);

    }

}