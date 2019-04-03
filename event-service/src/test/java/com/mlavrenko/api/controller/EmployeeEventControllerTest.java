package com.mlavrenko.api.controller;

import com.mlavrenko.api.domain.EmployeeEvent;
import com.mlavrenko.api.domain.enums.EmployeeStatus;
import com.mlavrenko.api.repository.EmployeeEventRepository;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Employee event controller test")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeEventControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @AfterEach
    void after() {
        employeeEventRepository.deleteAll();
    }

    @Test
    @DisplayName("getAllEventsByIdOrderedAscending should return empty collection and status 200 if zero entities was found")
    void getAllEventsByIdOrderedAscendingEmpty() throws Exception {

        mvc.perform(get("/event-service/employee-events/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @DisplayName("getAllEventsByIdOrderedAscending should return entities ordered by and status 200 if entities were found")
    void getAllEventsByIdOrderedAscending() throws Exception {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        createEvents(uuid);
        mvc.perform(get("/event-service/employee-events/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId", equalTo(id)))
                .andExpect(jsonPath("$[1].employeeId", equalTo(id)))
                .andExpect(jsonPath("$[0].status", equalTo(EmployeeStatus.CREATED.name())))
                .andExpect(jsonPath("$[1].status", equalTo(EmployeeStatus.UPDATED.name())))
                .andExpect(jsonPath("$[0].eventCreated", notNullValue()))
                .andExpect(jsonPath("$[1].eventCreated", notNullValue()));
    }

    private void createEvents(UUID uuid) {
        EmployeeEvent first = createEmployee(uuid);
        first.setStatus(EmployeeStatus.CREATED);
        first.setEventCreated(DateUtil.now());
        EmployeeEvent second = createEmployee(uuid);
        second.setStatus(EmployeeStatus.UPDATED);
        second.setEventCreated(DateUtil.now());
        employeeEventRepository.saveAll(Arrays.asList(first, second));
    }

    private EmployeeEvent createEmployee(UUID uuid) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        employeeEvent.setEmployeeId(uuid);
        return employeeEvent;
    }
}