package com.mlavrenko.api.service;

import static java.util.stream.Collectors.toList;

import com.mlavrenko.api.domain.EmployeeEvent;
import com.mlavrenko.api.dto.EmployeeEventDTO;
import com.mlavrenko.api.repository.EmployeeEventRepository;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeEventService {
    private final EmployeeEventRepository employeeEventRepository;

    public EmployeeEventService(EmployeeEventRepository employeeEventRepository) {
        this.employeeEventRepository = employeeEventRepository;
    }

    public List<EmployeeEventDTO> getAllEventsByIdOrderedAscending(UUID uuid) {
        return employeeEventRepository.findAllByEmployeeIdOrderByEventCreatedAsc(uuid).stream().map(this::convertToDTO).collect(toList());
    }

    private EmployeeEventDTO convertToDTO(EmployeeEvent employeeEvent) {
        EmployeeEventDTO employeeEventDTO = new EmployeeEventDTO();
        BeanUtils.copyProperties(employeeEvent, employeeEventDTO);
        return employeeEventDTO;
    }

    public void save(EmployeeEventDTO employeeEventDTO) {
        EmployeeEvent employeeEvent = convertToDomain(employeeEventDTO);
        employeeEventRepository.save(employeeEvent);
    }

    private EmployeeEvent convertToDomain(EmployeeEventDTO employeeEventDTO) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        BeanUtils.copyProperties(employeeEventDTO, employeeEvent);
        return employeeEvent;
    }
}
