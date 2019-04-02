package com.mlavrenko.api.controller;

import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.service.DepartmentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(value = "/employee-service/department",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO create(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }
}
