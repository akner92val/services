package com.mlavrenko.api.service;

import com.mlavrenko.api.notification.EmployeeEvent;
import com.mlavrenko.api.notification.KafkaEventProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@DisplayName("Message service test")
@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @Test
    @DisplayName("sendMessage should call producer to send given event to topic")
    void sendMessage() {
        MessageService messageService = new MessageService(kafkaEventProducer);

        messageService.sendMessage(Mockito.mock(EmployeeEvent.class));

        Mockito.verify(kafkaEventProducer).sendMessage(any(), any(EmployeeEvent.class));
    }
}