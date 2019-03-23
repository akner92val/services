package com.mlavrenko.api.service;

import com.mlavrenko.api.domain.Department;
import com.mlavrenko.api.dto.DepartmentDTO;
import com.mlavrenko.api.repository.DepartmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToDomain(departmentDTO);
        return convertToDTO(departmentRepository.save(department));
    }

    private DepartmentDTO convertToDTO(Department department) {
        if (department == null) {
            return null;
        } else {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            BeanUtils.copyProperties(department, departmentDTO);
            return departmentDTO;
        }
    }

    private Department convertToDomain(DepartmentDTO departmentDTO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        return department;
    }
}
