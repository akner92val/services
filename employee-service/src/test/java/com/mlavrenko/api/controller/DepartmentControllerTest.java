package com.mlavrenko.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Department controller test")
@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @AfterEach
    void after() {
        departmentRepository.deleteAll();
    }

    @Test
    @DisplayName("create should create new entity and return status 201")
    void create() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Department");
        String value = objectMapper.writeValueAsString(departmentDTO);
        mvc.perform(post("/employee-service/department")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(value)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo(departmentDTO.getName())));
    }
}