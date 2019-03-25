package com.mlavrenko.api.client;

import com.mlavrenko.api.dto.EmployeeDTO;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/employee")
public interface EmployeeApi {
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO employeeDTO);

    @RequestMapping(method = RequestMethod.PUT, value = "/{employeeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable("employeeId") Long employeeId);

    @RequestMapping(method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EmployeeDTO> delete(@Valid @RequestBody EmployeeDTO employeeDTO);

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EmployeeDTO> getById(@Valid @RequestBody UUID uuid);
}
