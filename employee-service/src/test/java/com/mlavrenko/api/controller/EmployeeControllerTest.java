package com.mlavrenko.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlavrenko.api.config.TestConfig;
import com.mlavrenko.api.domain.Employee;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.dto.EmployeeDTO;
import com.mlavrenko.api.repository.DepartmentRepository;
import com.mlavrenko.api.repository.EmployeeRepository;
import com.mlavrenko.api.service.DepartmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Employee controller test")
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void after() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    @DisplayName("create should create new entity and return status 201")
    void create() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        String value = objectMapper.writeValueAsString(employeeDTO);
        String content = mvc.perform(post("/employee-service/employee")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(value)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        EmployeeDTO actual = objectMapper.readValue(content, EmployeeDTO.class);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualToIgnoringGivenFields(employeeDTO, "id", "department"),
                () -> assertThat(actual.getDepartment()).isEqualToComparingFieldByField(employeeDTO.getDepartment())
        );
    }

    private EmployeeDTO getEmployeeDTO() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Department");
        DepartmentDTO department = departmentService.createDepartment(departmentDTO);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartment(department);
        employeeDTO.setEmail("Email");
        employeeDTO.setBirthDate(LocalDate.now());
        employeeDTO.setFullName("Name Surname");
        return employeeDTO;
    }

    @Test
    @DisplayName("update should return status not found when entity doesn't exist")
    void updateNotFound() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(UUID.randomUUID());
        String value = objectMapper.writeValueAsString(employeeDTO);
        mvc.perform(put("/employee-service/employee")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(value)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("update should return updated entity when entity exist")
    void update() throws Exception {
        EmployeeDTO employeeDTO = getExistedEmployeeDTO();
        employeeDTO.setFullName("New Name");
        String value = objectMapper.writeValueAsString(employeeDTO);
        String content = mvc.perform(put("/employee-service/employee")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(value)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        EmployeeDTO actual = objectMapper.readValue(content, EmployeeDTO.class);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualToIgnoringGivenFields(employeeDTO, "department"),
                () -> assertThat(actual.getDepartment()).isEqualToComparingFieldByField(employeeDTO.getDepartment())
        );

    }

    private EmployeeDTO getExistedEmployeeDTO() throws Exception {
        Employee employee = createEmployee();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO, "department");
        DepartmentDTO department = new DepartmentDTO();
        BeanUtils.copyProperties(employee.getDepartment(), department);
        employeeDTO.setDepartment(department);
        return employeeDTO;
    }

    private Employee createEmployee() throws Exception {
        create();
        return employeeRepository.findAll().stream()
                .findFirst()
                .orElseThrow(AssertionError::new);
    }

    @Test
    @DisplayName("delete should return status ok when entity exists and was deleted")
    void deleteOk() throws Exception {
        Employee employee = createEmployee();
        mvc.perform(delete("/employee-service/employee/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete should return status not found when entity doesn't exist")
    void deleteNotFound() throws Exception {
        mvc.perform(delete("/employee-service/employee/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getById should return status ok when entity exists")
    void getById() throws Exception {
        Employee employee = createEmployee();
        String content = mvc.perform(get("/employee-service/employee/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        EmployeeDTO actual = objectMapper.readValue(content, EmployeeDTO.class);

        assertAll(
                () -> assertThat(actual).isEqualToIgnoringGivenFields(employee, "department"),
                () -> assertThat(actual.getDepartment()).isEqualToComparingFieldByField(employee.getDepartment())
        );
    }

    @Test
    @DisplayName("getById should return status not found when entity doesn't exist")
    void getByIdNotFound() throws Exception {
        mvc.perform(get("/employee-service/employee/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}