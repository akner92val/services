package com.mlavrenko.api.service.notification;

import com.mlavrenko.api.dto.EmployeeEventDTO;
import com.mlavrenko.api.service.EmployeeEventService;
import com.mlavrenko.api.service.notification.helper.JsonHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
class EventConsumerService {
    private final EmployeeEventService employeeEventService;
    private final JsonHelper jsonHelper;

    EventConsumerService(EmployeeEventService employeeEventService, JsonHelper jsonHelper) {
        this.employeeEventService = employeeEventService;
        this.jsonHelper = jsonHelper;
    }

    void process(String content) {
        EmployeeEventDTO employeeEventDTO = jsonHelper.toValue(content, EmployeeEventDTO.class);

        employeeEventService.save(employeeEventDTO);
    }
}
