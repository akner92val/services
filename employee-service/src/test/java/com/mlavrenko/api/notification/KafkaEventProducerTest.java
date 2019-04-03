package com.mlavrenko.api.notification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("Kafka event producer test")
@ExtendWith(MockitoExtension.class)
class KafkaEventProducerTest {
    @Mock
    private KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    @Test
    @DisplayName("sendMessage should call kafkaTemplate to send given event to topic")
    void sendMessage() {
        KafkaEventProducer kafkaEventProducer = new KafkaEventProducer(kafkaTemplate);
        EmployeeEvent employeeEvent = mock(EmployeeEvent.class);
        String topic = "topic";

        kafkaEventProducer.sendMessage(topic, employeeEvent);

        verify(kafkaTemplate).send(topic, employeeEvent);
    }
}