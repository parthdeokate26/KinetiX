package com.kinetix.webhookservice.config;

import com.kinetix.webhookservice.dto.ExecutionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, ExecutionEvent> kafkaTemplate(
            ProducerFactory<String, ExecutionEvent> producerFactory) {

        return new KafkaTemplate<>(producerFactory);
    }
}