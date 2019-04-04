package com.mlavrenko.api.controller;

import com.mlavrenko.api.dto.EmployeeDTO;
import com.mlavrenko.api.service.EmployeeService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee-service/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO create(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO update(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable("id") UUID uuid) {
        employeeService.deleteEmployee(uuid);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getById(@Valid @PathVariable("id") UUID uuid) {
        return employeeService.getEmployeeById(uuid);
    }
}
