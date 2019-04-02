package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.Department;
import com.mlavrenko.api.domain.Employee;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.dto.EmployeeDTO;
import com.mlavrenko.api.notification.Message;
import com.mlavrenko.api.notification.EmployeeStatus;
import com.mlavrenko.api.repository.EmployeeRepository;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final MessageService messageService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService, MessageService messageService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.messageService = messageService;
    }

    public EmployeeDTO createEmployee(@Valid EmployeeDTO employeeDTO) {
        Employee employee = convertToDomain(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        messageService.sendMessage(new Message(saved.getUuid(), EmployeeStatus.CREATED));
        return convertToDto(saved);
    }

    public EmployeeDTO updateEmployee(@Valid EmployeeDTO employeeDTO) {
        Employee employee = getById(employeeDTO.getUuid());
        copyToEntity(employeeDTO, employee);
        Employee saved = employeeRepository.save(employee);
        messageService.sendMessage(new Message(saved.getUuid(), EmployeeStatus.UPDATED));
        return convertToDto(saved);
    }

    private Employee getById(UUID uuid) {
        return employeeRepository.findById(uuid).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteEmployee(@Valid UUID uuid) {
        employeeRepository.deleteById(uuid);
        messageService.sendMessage(new Message(uuid, EmployeeStatus.DELETED));
    }

    public EmployeeDTO getEmployeeById(@Valid UUID uuid) {
        return convertToDto(getById(uuid));
    }

    private void copyToEntity(@Valid EmployeeDTO employeeDTO, Employee employee) {
        BeanUtils.copyProperties(employeeDTO, employee, "department");
        BeanUtils.copyProperties(employeeDTO.getDepartment(), employee.getDepartment());
    }

    private Employee convertToDomain(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "uuid", "department");
        Department department = departmentService.getById(employeeDTO.getDepartment().getId()).orElseThrow(IllegalArgumentException::new);
        employee.setDepartment(department);
        return employee;
    }

    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO, "department");
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(employee.getDepartment(), departmentDTO);
        employeeDTO.setDepartment(departmentDTO);
        return employeeDTO;
    }
}
