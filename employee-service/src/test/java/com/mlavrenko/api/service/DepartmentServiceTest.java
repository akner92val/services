package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.Department;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("Department service test")
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    private DepartmentService departmentService;
    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void before() {
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    @DisplayName("createDepartment should return new entity")
    void createDepartment() {

        DepartmentDTO departmentDTO = new DepartmentDTO();
        Department expected = new Department();

        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(expected);

        DepartmentDTO actual = departmentService.createDepartment(departmentDTO);

        assertAll(
                () -> Mockito.verify(departmentRepository).save(any(Department.class)),
                () -> assertThat(actual).isEqualToComparingFieldByField(departmentDTO)
        );
    }

    @Test
    @DisplayName("getById should return expected entity when entity exists")
    void getById() {
        long id = 1L;
        Optional<Department> expected = Optional.ofNullable(Mockito.mock(Department.class));
        Mockito.when(departmentRepository.findById(id)).thenReturn(expected);

        Optional<Department> actual = departmentService.getById(id);
        assertAll(
                () -> Mockito.verify(departmentRepository).findById(anyLong()),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }
}