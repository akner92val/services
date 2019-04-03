package com.mlavrenko.api.repository;

import com.mlavrenko.api.domain.EmployeeEvent;
import com.mlavrenko.api.domain.enums.EmployeeStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DisplayName("employee event repository test")
@SpringBootTest
class EmployeeEventRepositoryTest {
    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Test
    @DisplayName("findAllByEmployeeIdOrderByEventCreatedAsc should return empty collection when zero entities with given criteria exist")
    void findAllByEmployeeIdOrderByEventCreatedAscEmpty() {
        List<EmployeeEvent> allByCriteria = employeeEventRepository.findAllByEmployeeIdOrderByEventCreatedAsc(UUID.randomUUID());

        Assertions.assertThat(allByCriteria).isEmpty();
    }

    @Test
    @DisplayName("findAllByEmployeeIdOrderByEventCreatedAsc should return expected entities when entities with given criteria exist")
    void findAllByOfferIdNotEmpty() {
        UUID uuid = UUID.randomUUID();
        EmployeeEvent first = getEmployeeEvent(uuid, EmployeeStatus.CREATED);
        EmployeeEvent second = getEmployeeEvent(uuid, EmployeeStatus.UPDATED);
        employeeEventRepository.saveAll(Arrays.asList(second, first));

        List<EmployeeEvent> allByCriteria = employeeEventRepository.findAllByEmployeeIdOrderByEventCreatedAsc(uuid);

        assertThat(allByCriteria).containsExactly(first, second);
    }

    private EmployeeEvent getEmployeeEvent(UUID uuid, EmployeeStatus status) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        employeeEvent.setEmployeeId(uuid);
        employeeEvent.setStatus(status);
        employeeEvent.setEventCreated(DateUtil.now());
        return employeeEvent;
    }
}