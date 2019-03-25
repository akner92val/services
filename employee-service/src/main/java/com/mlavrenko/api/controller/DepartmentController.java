package com.mlavrenko.api.controller;

import com.mlavrenko.api.client.DepartmentApi;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.service.DepartmentService;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class DepartmentController implements DepartmentApi {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public ResponseEntity<DepartmentDTO> create(@Valid DepartmentDTO departmentDTO) {
        DepartmentDTO department = departmentService.createDepartment(departmentDTO);
        URI uri = UriComponentsBuilder.fromHttpUrl(department.getRequiredLink(IanaLinkRelations.SELF).getHref()).build().toUri();

        return ResponseEntity.created(uri).body(department);
    }
}
