package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.Department;
import com.mlavrenko.api.domain.Employee;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.dto.EmployeeDTO;
import com.mlavrenko.api.notification.EmployeeEvent;
import com.mlavrenko.api.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Employee service test")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    private static final UUID ID = UUID.randomUUID();
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private MessageService messageService;

    @BeforeEach
    void before() {
        employeeService = new EmployeeService(employeeRepository, departmentService, messageService);
    }

    @Test
    @DisplayName("createEmployee should save new instance of employee in database and send message")
    void createEmployee() {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(null);
        Employee employee = getEmployee();
        Mockito.when(departmentService.getById(1L)).thenReturn(Optional.of(new Department()));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        EmployeeDTO actual = employeeService.createEmployee(employeeDTO);

        assertAll(
                () -> assertThat(actual).isEqualToIgnoringGivenFields(employeeDTO, "id", "department"),
                () -> assertThat(actual.getDepartment()).isEqualToComparingFieldByField(employeeDTO.getDepartment()),
                () -> assertThat(actual.getId()).isNotNull(),
                () -> Mockito.verify(employeeRepository).save(Mockito.any(Employee.class)),
                () -> Mockito.verify(departmentService).getById(anyLong()),
                () -> Mockito.verify(messageService).sendMessage(any(EmployeeEvent.class))
        );
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setBirthDate(LocalDate.now());
        employee.setEmail("Email");
        employee.setFullName("Full Name");
        Department department = new Department();
        department.setName("Department");
        department.setId(1L);
        employee.setDepartment(department);
        employee.setId(ID);
        return employee;
    }

    private EmployeeDTO getEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setBirthDate(LocalDate.now());
        employeeDTO.setEmail("Email");
        employeeDTO.setFullName("Full Name");
        DepartmentDTO department = new DepartmentDTO();
        department.setName("Department");
        department.setId(1L);
        employeeDTO.setDepartment(department);
        employeeDTO.setId(ID);
        return employeeDTO;
    }

    @Test
    @DisplayName("updateEmployee should update entity and send message if entity exists")
    void updateEmployee() {
        String newName = "New Name";
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setFullName(newName);
        Employee employee = getEmployee();
        Employee expected = getEmployee();
        expected.setFullName(newName);
        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(expected);

        EmployeeDTO actual = employeeService.updateEmployee(employeeDTO);

        assertAll(
                () -> assertThat(actual).isEqualToIgnoringGivenFields(employeeDTO, "department"),
                () -> assertThat(actual.getDepartment()).isEqualToComparingFieldByField(employeeDTO.getDepartment()),
                () -> Mockito.verify(employeeRepository).save(Mockito.any(Employee.class)),
                () -> Mockito.verifyZeroInteractions(departmentService),
                () -> Mockito.verify(messageService).sendMessage(any(EmployeeEvent.class))
        );
    }

    @Test
    @DisplayName("updateEmployee should throw expected exception when entity doesn't exist")
    void updateEmployeeNotFound() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(ID);
        when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.updateEmployee(employeeDTO));
    }

    @Test
    @DisplayName("deleteEmployee should call repository's delete and send a message when entity exists")
    void deleteEmployee() {
        when(employeeRepository.existsById(ID)).thenReturn(true);

        employeeService.deleteEmployee(ID);

        assertAll(
                () -> verify(employeeRepository).existsById(ID),
                () -> verify(employeeRepository).deleteById(ID),
                () -> verify(messageService).sendMessage(any(EmployeeEvent.class))
        );
    }

    @Test
    @DisplayName("deleteEmployee should throw expected exception and not send message when entity doesn't exist")
    void deleteEmployeeNotFound() {
        when(employeeRepository.existsById(ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(ID));
        verifyZeroInteractions(messageService);
    }

    @Test
    @DisplayName("getEmployeeById should call repository's getById when entity exists")
    void getEmployeeById() {
        Employee employee = new Employee();
        employee.setDepartment(new Department());
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));

        EmployeeDTO actual = employeeService.getEmployeeById(ID);

        assertAll(
                () -> verify(employeeRepository).findById(ID),
                () -> assertThat(actual).isNotNull()
        );
    }

    @Test
    @DisplayName("getById should throw expected exception when entity doesn't exist")
    void getByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(ID));
    }

}