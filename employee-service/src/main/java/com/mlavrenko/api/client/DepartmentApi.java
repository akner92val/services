package com.mlavrenko.api.client;

import com.mlavrenko.api.dto.DepartmentDTO;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public interface DepartmentApi {
    @RequestMapping(method = RequestMethod.POST, value = "/department",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<DepartmentDTO> create(@Valid @RequestBody DepartmentDTO departmentDTO);

}
