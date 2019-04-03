package com.mlavrenko.api.controller;

import com.mlavrenko.api.dto.EmployeeEventDTO;
import com.mlavrenko.api.service.EmployeeEventService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeEventController {
    private final EmployeeEventService employeeEventService;

    public EmployeeEventController(EmployeeEventService employeeEventService) {
        this.employeeEventService = employeeEventService;
    }

    @GetMapping(value = "/event-service/employee-events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeEventDTO> getAllEventsByIdOrderedAscending(@Valid @PathVariable("id") UUID uuid) {
        return employeeEventService.getAllEventsByIdOrderedAscending(uuid);
    }
}
