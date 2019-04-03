package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.EmployeeEvent;
import com.mlavrenko.api.dto.EmployeeEventDTO;
import com.mlavrenko.api.repository.EmployeeEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class EmployeeEventService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final EmployeeEventRepository employeeEventRepository;

    public EmployeeEventService(EmployeeEventRepository employeeEventRepository) {
        this.employeeEventRepository = employeeEventRepository;
    }

    public List<EmployeeEventDTO> getAllEventsByIdOrderedAscending(UUID uuid) {
        logger.info("Get all events for id: {}", uuid);
        List<EmployeeEventDTO> eventsById = employeeEventRepository.findAllByEmployeeIdOrderByEventCreatedAsc(uuid)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
        logger.debug("Retrieved events: {}", eventsById);
        return eventsById;
    }

    private EmployeeEventDTO convertToDTO(EmployeeEvent employeeEvent) {
        EmployeeEventDTO employeeEventDTO = new EmployeeEventDTO();
        BeanUtils.copyProperties(employeeEvent, employeeEventDTO, "id");
        return employeeEventDTO;
    }

    public void save(EmployeeEventDTO employeeEventDTO) {
        logger.info("Saving employee = {}", employeeEventDTO);
        EmployeeEvent employeeEvent = convertToDomain(employeeEventDTO);
        EmployeeEvent saved = employeeEventRepository.save(employeeEvent);
        logger.debug("Saved employee = {}", saved);
    }

    private EmployeeEvent convertToDomain(EmployeeEventDTO employeeEventDTO) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        BeanUtils.copyProperties(employeeEventDTO, employeeEvent);
        return employeeEvent;
    }
}
