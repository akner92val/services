package com.mlavrenko.api.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
public class KafkaEventProducer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, EmployeeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EmployeeEvent employeeEvent) {
        log.info("Sending message to topic {}, message:\n{}", topic, employeeEvent);
        kafkaTemplate.send(topic, employeeEvent);
    }
}
