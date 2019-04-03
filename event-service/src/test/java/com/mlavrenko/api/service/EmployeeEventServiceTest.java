package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.EmployeeEvent;
import com.mlavrenko.api.dto.EmployeeEventDTO;
import com.mlavrenko.api.repository.EmployeeEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Employee event service test")
@ExtendWith(MockitoExtension.class)
class EmployeeEventServiceTest {
    private EmployeeEventService employeeEventService;
    @Mock
    private EmployeeEventRepository employeeEventRepository;

    @BeforeEach
    void before() {
        employeeEventService = new EmployeeEventService(employeeEventRepository);
    }

    @Test
    @DisplayName("getAllEventsByIdOrderedAscending should return expected entities")
    void getAllEventsByIdOrderedAscending() {
        UUID uuid = UUID.randomUUID();
        List<EmployeeEvent> events = Collections.singletonList(new EmployeeEvent());
        when(employeeEventRepository.findAllByEmployeeIdOrderByEventCreatedAsc(uuid)).thenReturn(events);

        List<EmployeeEventDTO> actual = employeeEventService.getAllEventsByIdOrderedAscending(uuid);

        assertAll(
                () -> Mockito.verify(employeeEventRepository).findAllByEmployeeIdOrderByEventCreatedAsc(uuid),
                () -> assertThat(actual).hasSize(events.size())
        );
    }

    @Test
    @DisplayName("save should save entity")
    void save() {
        EmployeeEventDTO employeeEventDTO = new EmployeeEventDTO();
        EmployeeEvent expected = new EmployeeEvent();

        when(employeeEventRepository.save(any(EmployeeEvent.class))).thenReturn(expected);

        employeeEventService.save(employeeEventDTO);

        Mockito.verify(employeeEventRepository).save(any(EmployeeEvent.class));
    }
}